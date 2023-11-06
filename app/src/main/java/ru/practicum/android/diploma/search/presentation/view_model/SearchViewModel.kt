package ru.practicum.android.diploma.search.presentation.view_model

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.search.domain.SearchInteractor
import ru.practicum.android.diploma.search.domain.models.QuerySearchMdl

class SearchViewModel(private val searchInteractor: SearchInteractor): ViewModel() {

 fun getTestRequest(request: QuerySearchMdl){
     viewModelScope.launch {
         searchInteractor.getTestRequest(request).collect{
             Log.d("VacancyName", it.listVacancy[0].vacancyName)
         }

     }
 }
}