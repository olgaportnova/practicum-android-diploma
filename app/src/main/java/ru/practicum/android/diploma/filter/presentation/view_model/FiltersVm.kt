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

    private var filtersSettings: FilterData = filtersController.getDefaultSettings()
    private var newFilterSet = filtersController.getDefaultSettings()

    private fun FilterData.updateParams(
        idCountry: String? = null,
        idArea: String? = null,
        idIndustry: String? = null,
        nameCountry: String? = null,
        nameArea: String? = null,
        nameIndustry: String? = null,
        currency: String? = null,
        salary: Int? = null,
        onlyWithSalary: Boolean? = null
    ): FilterData {
        return this.copy(
            idCountry = idCountry ?: this.idCountry,
            idArea = idArea ?: this.idArea,
            idIndustry = idIndustry ?: this.idIndustry,
            nameCountry = nameCountry ?: this.nameCountry,
            nameArea = nameArea ?: this.nameArea,
            nameIndustry = nameIndustry ?: this.nameIndustry,
            currency = currency ?: this.currency,
            salary = salary ?: this.salary,
            onlyWithSalary = onlyWithSalary ?: this.onlyWithSalary,
        )
    }

    init {
        loadFilterSet()
    }

    private fun loadFilterSet() {
        filtersSettings = filtersController.getFilterSettings()
        Log.e("LOG", filtersSettings.toString())

        _screenState.value = ScreenState.FilterSettings(filtersSettings)

    }

    fun setNewSalaryToFilter(newSalary: CharSequence?) {
        val salary =50// newSalary.toString().toInt()
        newFilterSet = newFilterSet.updateParams(salary = salary)
        _screenState.value = ScreenState.FilterSettings(newFilterSet)
    }

    fun setWithSalaryParam(withSalaryParam: Boolean) {
        newFilterSet = newFilterSet.updateParams(onlyWithSalary = withSalaryParam)
        _screenState.value = ScreenState.FilterSettings(newFilterSet)
    }

    fun setNewCountryToFilter(name: String?, id: Int): Boolean {
        if (name.isNullOrEmpty()) return false
        else {
            newFilterSet = newFilterSet.updateParams(idCountry = id.toString())
            newFilterSet = newFilterSet.updateParams(nameCountry = name)
            _screenState.value = ScreenState.FilterSettings(newFilterSet)
        }
        return true
    }

    fun setNewAreToFilter(name: String?, id: Int): Boolean {
        if (name.isNullOrEmpty()) return false
        else {
            newFilterSet = newFilterSet.updateParams(idArea = id.toString())
            newFilterSet = newFilterSet.updateParams(nameArea = name)
            _screenState.value = ScreenState.FilterSettings(newFilterSet)
        }
        return true
    }

    fun setNewIndustryToFilter(name: String?, id: Int): Boolean {
        if (name.isNullOrEmpty()) return false
        else {
            newFilterSet = newFilterSet.updateParams(idIndustry = id.toString())
            newFilterSet = newFilterSet.updateParams(nameIndustry = name)
            _screenState.value = ScreenState.FilterSettings(newFilterSet)
        }
        return true
    }

}