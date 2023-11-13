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
import ru.practicum.android.diploma.search.presentation.states.ToastState
import ru.practicum.android.diploma.util.DataStatus

class SearchViewModel(private val searchInteractor: SearchInteractor) : ViewModel() {

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY_ML = 2000L
        private const val TOAST_DEBOUNCE_DELAY_ML = 10000L
    }


   /* private val _stateFilters = MutableStateFlow<StateSearch>(StateSearch.Default)
    val stateFilters = _stateFilters as StateFlow<StateSearch>
*/
    private val _stateSearch = MutableStateFlow<StateSearch>(StateSearch.Default)
    val stateSearch = _stateSearch.asStateFlow()

    /*  private val _stateToast = MutableStateFlow<ToastState>(ToastState.NoneMessage)
    val stateToast = _stateToast as StateFlow<ToastState>
*/
    private var searchJob: Job? = null
    private var isShowToast: Boolean = true
    fun doRequestSearch(modelForQuery: QuerySearchMdl) {

        if (modelForQuery.text.length != 1) {
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
    }

    fun getParamsFilters(): FilterData? {
        return searchInteractor.getParamsFilters()
       /* if (params == null) {
            _stateSearch.value = StateSearch.NoUseFilters
        } else {
            _stateSearch.value = StateSearch.UseFilters(params)
        }*/
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

/*    fun setToastNoMessage() {
        _stateSearch.value = StateSearch.NoneMessage
    }

    fun showToast(message: String) {
        _stateSearch.value = StateSearch.ShowMessage(message)
    }

    fun showToastDebounce(message: String) {
        if (isShowToast) {
            isShowToast = false
            showToast(message)
            viewModelScope.launch {
                delay(TOAST_DEBOUNCE_DELAY_ML)
                isShowToast = true
            }
        }
    }*/

}