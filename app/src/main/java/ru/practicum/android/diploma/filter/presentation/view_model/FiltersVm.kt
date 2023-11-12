package ru.practicum.android.diploma.filter.presentation.view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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




    init {
        loadFilterSet()
    }

    private fun loadFilterSet() {
        // Загружаем данные из SharedPrefs
        oldFiltersSet = filtersController.getFilterSettings()
        newFilterSet = oldFiltersSet.copy()

        // Invalidate screen
        _screenState.value = ScreenState.FilterSettings(oldFiltersSet,compareFilters())
        compareFilters()
    }

    fun getFilters() = newFilterSet

    fun setNewSalaryToFilter(newSalary: CharSequence?) {
        val salary = 50// newSalary.toString().toInt()
        newFilterSet = newFilterSet.copy(salary = salary)

        // Invalidate screen
        _screenState.value = ScreenState.FilterSettings(newFilterSet,compareFilters())
    }

    fun setWithSalaryParam() {
        val isChecked = newFilterSet.onlyWithSalary
        newFilterSet = newFilterSet.copy(onlyWithSalary = !isChecked)

        // Invalidate screen
        _screenState.value = ScreenState.FilterSettings(newFilterSet,compareFilters())
    }

    fun updateFiltersWithRemote(receivedFilterSettings: FilterData) {
        this.newFilterSet = receivedFilterSettings

        // Invalidate screen
        _screenState.value = ScreenState.FilterSettings(newFilterSet,compareFilters())
    }

    private fun compareFilters():Boolean {
       return oldFiltersSet != newFilterSet
    }

    fun saveNewFilterSet() {
        filtersController.saveFilterSettings(newFilterSet)

        oldFiltersSet = newFilterSet.copy()

        // Invalidate screen
        _screenState.value = ScreenState.FilterSettings(newFilterSet,compareFilters())
    }

    fun abortFilters() {
        newFilterSet = oldFiltersSet.copy()

        // Invalidate screen
        _screenState.value = ScreenState.FilterSettings(newFilterSet,compareFilters())
    }
}

