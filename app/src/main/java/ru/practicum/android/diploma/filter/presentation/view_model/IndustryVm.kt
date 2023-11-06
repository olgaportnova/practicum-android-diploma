package ru.practicum.android.diploma.filter.presentation.view_model

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.filter.domain.interfaces.IndustriesController
import ru.practicum.android.diploma.filter.presentation.util.ScreenState
import ru.practicum.android.diploma.util.DataStatus

class IndustryVm(private val industriesController: IndustriesController) : DefaultViewModel() {

    init {
        loadIndustries()
    }
    private fun loadIndustries(){
        viewModelScope.launch {
            industriesController.getIndustries().collect{
                when (it) {
                    is DataStatus.Loading -> _screenState.value = ScreenState.Loading(null)
                    is DataStatus.Content -> _screenState.value = ScreenState.Content(it.data!!.map { category->
                        professionToAbstract(category)
                    })
                    else -> {}
                }
            }
        }
    }
}