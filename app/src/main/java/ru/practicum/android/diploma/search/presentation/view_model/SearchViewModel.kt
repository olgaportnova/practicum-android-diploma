package ru.practicum.android.diploma.search.presentation.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.search.domain.SearchInteractor
import ru.practicum.android.diploma.search.domain.models.AnswerVacancyList
import ru.practicum.android.diploma.search.domain.models.QuerySearchMdl
import ru.practicum.android.diploma.search.presentation.states.StateFilters
import ru.practicum.android.diploma.util.DataStatus

class SearchViewModel(private val searchInteractor: SearchInteractor): ViewModel() {

    val _stateFilters = MutableStateFlow<StateFilters>(StateFilters.NoUseFilters)
    val stateFilters = _stateFilters as StateFlow<StateFilters>

    val _stateSearch = MutableStateFlow<DataStatus<AnswerVacancyList>>(DataStatus.Default())
    val stateSearch = _stateSearch as StateFlow<DataStatus<AnswerVacancyList>>

    fun doRequestSearch(modelForQuery: QuerySearchMdl){

        viewModelScope.launch {
            searchInteractor.doRequestSearch(modelForQuery).collect{
                 when(it){
                   is DataStatus.Content -> {
                       _stateSearch.value = DataStatus.Content(it.data!!)
                   }
                   is DataStatus.Loading ->{
                       _stateSearch.value = DataStatus.Loading()
                   }
                   is DataStatus.Error -> {
                       if (it.code == 0)
                       _stateSearch.value  = DataStatus.Error(errorMessage = R.string.error_app_search_log.toString())
                       else
                       _stateSearch.value = DataStatus.Error(errorMessage = "${R.string.error_sever_log} ${it.code}")
                   }
                   is DataStatus.EmptyContent -> {
                       _stateSearch.value = DataStatus.EmptyContent()
                   }
                   is DataStatus.NoConnecting ->{
                       _stateSearch.value = DataStatus.NoConnecting()
                   }
                   is DataStatus.Default ->{
                       _stateSearch.value = DataStatus.Default()
                   }
                   else -> {
                       _stateSearch.value = DataStatus.Default()
                   }

                 }
            }
        }

    }

    fun getParamsFilters(){
        val params = searchInteractor.getParamsFilters()

        if(params == null) _stateFilters.value = StateFilters.NoUseFilters
        else _stateFilters.value = StateFilters.UseFilters(params)
    }

}