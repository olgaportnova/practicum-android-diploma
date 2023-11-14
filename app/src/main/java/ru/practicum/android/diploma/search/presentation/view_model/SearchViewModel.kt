package ru.practicum.android.diploma.search.presentation.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.filter.domain.models.FilterData
import ru.practicum.android.diploma.search.domain.SearchInteractor
import ru.practicum.android.diploma.search.domain.models.QuerySearchMdl
import ru.practicum.android.diploma.search.presentation.states.StateSearch
import ru.practicum.android.diploma.util.DataStatus

class SearchViewModel(private val searchInteractor: SearchInteractor) : ViewModel() {

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY_ML = 2000L
    }

    private val _stateSearch = MutableStateFlow<StateSearch>(StateSearch.Default)
    val stateSearch = _stateSearch.asStateFlow()

    private var searchJob: Job? = null
    fun doRequestSearch(modelForQuery: QuerySearchMdl) {

            if (modelForQuery.text != "") {
                viewModelScope.launch {
                    _stateSearch.value = StateSearch.Loading
                    searchInteractor.doRequestSearch(modelForQuery).collect {
                      when (it) {
                            is DataStatus.Content -> {_stateSearch.value = StateSearch.Content(it.data!!)}
                            is DataStatus.Error -> {_stateSearch.value = StateSearch.Error(it.code)}
                            is DataStatus.EmptyContent -> {_stateSearch.value = StateSearch.EmptyContent}
                            is DataStatus.NoConnecting -> {_stateSearch.value = StateSearch.NoConnecting}
                            is DataStatus.Default -> {_stateSearch.value = StateSearch.Default}
                            else -> {_stateSearch.value = StateSearch.Default}
                        }
                    }
                }
            }
    }

    fun getParamsFilters(): FilterData? {
        return searchInteractor.getParamsFilters()
    }

    fun searchDebounce(modelForQuery: QuerySearchMdl) {
        searchJob?.cancel()

        searchJob = viewModelScope.launch {
                if (modelForQuery.text != "") {
                    delay(SEARCH_DEBOUNCE_DELAY_ML)
                    doRequestSearch(modelForQuery)
                }
        }
    }

    fun setDefaultState() {
        _stateSearch.value = StateSearch.Default
    }
}