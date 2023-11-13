package ru.practicum.android.diploma.filter.presentation.view_model

import android.util.Log
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

    private val screenStateFilterSettings = MutableLiveData<FilterData>()
    var filtersSettings: FilterData = filtersController.getDefaultSettings()

    init {
        loadFilterSet()
    }

    private fun loadFilterSet() {
        filtersSettings = filtersController.getFilterSettings()
        screenStateFilterSettings.value = filtersSettings
    }

    fun chooseAnotherCountry(newCountry: AreaData?) {
        _countryChosen.value = newCountry
        checkAcceptCondition()
    }

    fun chooseAnotherDistrict(newDistrict: AreaData?) {
        _districtChosen.value = newDistrict
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