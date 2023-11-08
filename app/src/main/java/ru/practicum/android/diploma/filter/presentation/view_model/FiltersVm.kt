package ru.practicum.android.diploma.filter.presentation.view_model

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ru.practicum.android.diploma.filter.domain.interfaces.FiltersController
import ru.practicum.android.diploma.filter.domain.models.FilterData
import ru.practicum.android.diploma.filter.presentation.util.ScreenState

class FiltersVm(private val filtersController: FiltersController) : ViewModel() {

    private val _screenState = MutableStateFlow<ScreenState>(ScreenState.Loading(null))
    val screenState = _screenState as StateFlow<ScreenState>


    fun loadFilterSet(){
        val fSet = filtersController.getFilterSettings()
        Log.e("Log",fSet.toString())

        if (fSet == null) safeDefaultFilter()
        else _screenState.value = ScreenState.FilterSettings(fSet)
    }

    private fun safeDefaultFilter() {
        val defaultSettings = FilterData(
            idCountry = "-1",
            idArea = "-1",
            idIndustry = "-1",
            nameCountry = null,
            nameArea = null,
            nameIndustry = null,
            currency = null,
            salary = -1,
            onlyWithSalary = false,
        )
        filtersController.saveFilterSettings(defaultSettings)
        _screenState.value = ScreenState.FilterSettings(defaultSettings)
    }
}