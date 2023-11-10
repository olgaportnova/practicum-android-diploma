package ru.practicum.android.diploma.filter.presentation.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.filter.domain.interfaces.FiltersController
import ru.practicum.android.diploma.filter.domain.models.AreaData
import ru.practicum.android.diploma.filter.domain.models.FilterData

class WorkPlaceVm(private val filtersController: FiltersController) : ViewModel() {
    private val _countryChosen = MutableLiveData<AreaData?>(null)
    val countryChosen = _countryChosen as LiveData<AreaData?>

    private val _districtChosen = MutableLiveData<AreaData?>(null)
    val districtChosen = _districtChosen as LiveData<AreaData?>

    private val _acceptChanges = MutableLiveData<Boolean>(false)
    val acceptChanges = _acceptChanges as LiveData<Boolean>

    private val _errorMsg = MutableLiveData<String>()
    val errorMsg = _errorMsg as LiveData<String>

    val screenStateFilterSettings = MutableLiveData<FilterData>()
    var filtersSettings: FilterData = filtersController.getDefaultSettings()

    init {
        loadFilterSet()
    }

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

    private fun loadFilterSet() {
        filtersSettings = filtersController.getFilterSettings()
        screenStateFilterSettings.value = filtersSettings
    }

    private fun saveAreaToFilter(areaToSave: AreaData, areaType: Int) {
        val newSet = when (areaType) {
            AREA_TYPE_AREA -> {
                filtersSettings.updateParams(
                    idArea = areaToSave.id.toString(),
                    nameArea = areaToSave.name
                )
            }

            AREA_TYPE_COUNTRY -> {
                filtersSettings.updateParams(
                    idCountry = areaToSave.id.toString(),
                    nameCountry = areaToSave.name
                )
            }
            else -> filtersSettings
        }

        filtersController.saveFilterSettings(newSet)
    }

    fun chooseAnotherCountry(newCountry: AreaData?) {
        _countryChosen.value = newCountry
        if (newCountry != null) saveAreaToFilter(
            areaToSave = newCountry,
            areaType = AREA_TYPE_COUNTRY
        )

        checkAcceptCondition()
    }

    fun chooseAnotherDistrict(newDistrict: AreaData?) {
        _districtChosen.value = newDistrict

        if (newDistrict != null) saveAreaToFilter(
            areaToSave = newDistrict,
            areaType = AREA_TYPE_AREA
        )

        checkAcceptCondition()
    }

    private fun checkAcceptCondition() {
        _acceptChanges.value = _countryChosen.value != null || _districtChosen.value != null
    }

    companion object {
        const val AREA_TYPE_AREA = 0
        const val AREA_TYPE_COUNTRY = 1
    }
}