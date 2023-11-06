package ru.practicum.android.diploma.filter.presentation.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.filter.domain.interfaces.IndustriesController
import ru.practicum.android.diploma.util.DataStatus

class IndustryVm(private val industriesController: IndustriesController):ViewModel() {
    private val _errorMsg = MutableLiveData<String>()
    val errorMsg = _errorMsg as LiveData<String>
    init {
        viewModelScope.launch {
            industriesController.getIndustries().collect{
                if(it is DataStatus.Loading) _errorMsg.value = "loading"
            }
        }

    }
}