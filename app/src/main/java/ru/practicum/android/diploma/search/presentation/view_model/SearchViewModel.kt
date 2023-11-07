package ru.practicum.android.diploma.search.presentation.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.search.domain.SearchInteractor
import ru.practicum.android.diploma.search.domain.models.AnswerVacancyList
import ru.practicum.android.diploma.search.domain.models.QuerySearchMdl
import ru.practicum.android.diploma.search.presentation.states.StateFilters
import ru.practicum.android.diploma.util.DataStatus

class SearchViewModel(private val searchInteractor: SearchInteractor): ViewModel() {

    val _stateFilters = MutableStateFlow<StateFilters>(StateFilters.NO_USE_FILTERS)
    val stateFilters = _stateFilters as StateFlow<StateFilters>

    val _stateSearch = MutableStateFlow<DataStatus<AnswerVacancyList>>(DataStatus.Default())
    val stateSearch = _stateSearch as StateFlow<DataStatus<AnswerVacancyList>>

    fun doRequestSearch(modelForQuery: QuerySearchMdl){

        viewModelScope.launch {
            searchInteractor.doRequestSearch(modelForQuery).collect{

            }
        }

    }

}