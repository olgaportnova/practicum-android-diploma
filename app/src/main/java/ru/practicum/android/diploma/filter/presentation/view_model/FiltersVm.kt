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

    var filtersSettings:FilterData = filtersController.getDefaultSettings()

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

    fun loadFilterSet() {
        filtersSettings= filtersController.getFilterSettings()
            _screenState.value = ScreenState.FilterSettings(filtersSettings)
        Log.e("LOG",filtersSettings.toString())
    }

    fun setNewSalaryToFilter(newSalary:Int){
        filtersController.saveFilterSettings(filtersSettings.updateParams(salary = newSalary))
    }

    fun setWithSalaryParam(withSalaryParam:Boolean){
        filtersController.saveFilterSettings(filtersSettings.updateParams(onlyWithSalary = withSalaryParam))
    }
}