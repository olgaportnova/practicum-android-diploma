package ru.practicum.android.diploma.filter.presentation.view_model

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ru.practicum.android.diploma.filter.domain.interfaces.FiltersController
import ru.practicum.android.diploma.filter.domain.models.FilterData
import ru.practicum.android.diploma.filter.presentation.fragment.ScreenFiltersState

class FiltersVm(private val filtersController: FiltersController) : ViewModel() {

    private val _screenState =
        MutableStateFlow<ScreenFiltersState>(ScreenFiltersState.Loading(null))
    val screenState = _screenState as StateFlow<ScreenFiltersState>

    private val defaultFilterSet = filtersController.getDefaultSettings()
    private var oldFiltersSet = defaultFilterSet.copy()
    private var newFilterSet = defaultFilterSet.copy()

    var userInput = false


    init {
        loadFilterSet()
    }

    private fun loadFilterSet() {
        // Загружаем данные из SharedPrefs
        oldFiltersSet = filtersController.getFilterSettings()
        newFilterSet = oldFiltersSet.copy()

        // Invalidate screen
        _screenState.value =
            ScreenFiltersState.Content(oldFiltersSet, compareFilters(), compareFiltersWithDefault())
    }

    fun getFilters() = newFilterSet

    fun setNewSalaryToFilter(incomeStr: CharSequence?): String {
        if (incomeStr.isNullOrEmpty()) {
            // If text from salaryInput has been cleared via delete button
            // Set salary to zero
            newFilterSet = newFilterSet.copy(salary = 0)

            // Invalidate screen (to update accept_button visibility
            _screenState.value = ScreenFiltersState.Content(
                newFilterSet,
                compareFilters(),
                compareFiltersWithDefault()
            )
            return "0"
        }

        val newSalary = incomeStr.toString().toIntOrNull()

        return if (newSalary !== null && newSalary >= 0) {
            // if inputSalary is correct, save salary in new filter set
            newFilterSet = newFilterSet.copy(salary = newSalary)

            // Invalidate screen (to update accept_button visibility
            _screenState.value = ScreenFiltersState.Content(
                newFilterSet,
                compareFilters(),
                compareFiltersWithDefault()
            )

            newFilterSet.salary.toString()
        } else {
            newFilterSet.salary.toString()
        }
    }

    fun setWithSalaryParam(isChecked: Boolean) {
        newFilterSet = newFilterSet.copy(onlyWithSalary = isChecked)

        // Invalidate screen
        _screenState.value =
            ScreenFiltersState.Content(newFilterSet, compareFilters(), compareFiltersWithDefault())
    }

    fun updateFiltersWithRemote(receivedFilterSettings: FilterData) {
        this.newFilterSet = receivedFilterSettings

        // Invalidate screen
        _screenState.value =
            ScreenFiltersState.Content(newFilterSet, compareFilters(), compareFiltersWithDefault())
    }

    private fun compareFilters(): Boolean {
        return oldFiltersSet != newFilterSet
    }

    private fun compareFiltersWithDefault(): Boolean {
        return defaultFilterSet != newFilterSet
    }


    fun saveNewFilterSet() {
        filtersController.saveFilterSettings(newFilterSet)

        oldFiltersSet = newFilterSet.copy()

        // Invalidate screen
        _screenState.value =
            ScreenFiltersState.Content(newFilterSet, compareFilters(), compareFiltersWithDefault())
    }

    fun clearWorkPlace() {
        newFilterSet =
            newFilterSet.copy(idCountry = null, idArea = null, nameCountry = null, nameArea = null)

        // Invalidate screen
        _screenState.value =
            ScreenFiltersState.Content(newFilterSet, compareFilters(), compareFiltersWithDefault())
    }

    fun clearIndustry() {
        newFilterSet = newFilterSet.copy(idIndustry = null, nameIndustry = null)

        // Invalidate screen
        _screenState.value =
            ScreenFiltersState.Content(newFilterSet, compareFilters(), compareFiltersWithDefault())
    }

    fun abortFilters() {
        newFilterSet = defaultFilterSet.copy()
        oldFiltersSet = defaultFilterSet.copy()
        filtersController.saveFilterSettings(newFilterSet)

        // Invalidate screen
        userInput = true
        _screenState.value =
            ScreenFiltersState.Content(oldFiltersSet, compareFilters(), compareFiltersWithDefault())
    }
}

