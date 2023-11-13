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

    private var oldFiltersSet = filtersController.getDefaultSettings()
    private var newFilterSet = filtersController.getDefaultSettings()

    var userInput = false


    init {
        Log.e("LOG","Init")
        val num = "-45".toIntOrNull()
        Log.e("LOG","num = $num")

        loadFilterSet()
    }

    private fun loadFilterSet() {
        // Загружаем данные из SharedPrefs
        oldFiltersSet = filtersController.getFilterSettings()
        newFilterSet = oldFiltersSet.copy()

        // Invalidate screen
        _screenState.value = ScreenState.FilterSettings(oldFiltersSet, compareFilters())
    }

    fun getFilters() = newFilterSet

    fun setNewSalaryToFilter(incomeStr: CharSequence?):String {
        if(incomeStr.isNullOrEmpty()){
            // If text from salaryInput has been cleared via delete button
            // Set salary to zero
            newFilterSet = newFilterSet.copy(salary = 0)

            // Invalidate screen (to update accept_button visibility
            _screenState.value = ScreenState.FilterSettings(newFilterSet, compareFilters())
            return "0"
        }

        val newSalary = incomeStr.toString().toIntOrNull()

        return if(newSalary!==null && newSalary>=0){
            // if inputSalary is correct, save salary in new filter set
            newFilterSet = newFilterSet.copy(salary = newSalary)

            // Invalidate screen (to update accept_button visibility
            _screenState.value = ScreenState.FilterSettings(newFilterSet, compareFilters())

            newFilterSet.salary.toString()
        } else{
            newFilterSet.salary.toString()
        }
    }

    fun setWithSalaryParam(isChecked: Boolean) {
        newFilterSet = newFilterSet.copy(onlyWithSalary = isChecked)

        // Invalidate screen
        _screenState.value = ScreenState.FilterSettings(newFilterSet, compareFilters())
    }

    fun updateFiltersWithRemote(receivedFilterSettings: FilterData) {
        this.newFilterSet = receivedFilterSettings

        // Invalidate screen
        _screenState.value = ScreenState.FilterSettings(newFilterSet, compareFilters())
    }

    private fun compareFilters(): Boolean {
        return oldFiltersSet != newFilterSet
    }

    fun saveNewFilterSet() {
        filtersController.saveFilterSettings(newFilterSet)

        oldFiltersSet = newFilterSet.copy()

        // Invalidate screen
        _screenState.value = ScreenState.FilterSettings(newFilterSet, compareFilters())
    }

    fun clearWorkPlace(){
        newFilterSet = newFilterSet.copy(idCountry = null,idArea = null, nameCountry = null, nameArea = null)

        // Invalidate screen
        _screenState.value = ScreenState.FilterSettings(newFilterSet, compareFilters())
    }

    fun clearIndustry(){
        newFilterSet = newFilterSet.copy(idIndustry = null, nameIndustry = null)

        // Invalidate screen
        _screenState.value = ScreenState.FilterSettings(newFilterSet, compareFilters())
    }

    fun abortFilters() {
        newFilterSet = oldFiltersSet.copy()

        // Invalidate screen
        userInput = true
        _screenState.value = ScreenState.FilterSettings(newFilterSet, compareFilters())
    }
}

