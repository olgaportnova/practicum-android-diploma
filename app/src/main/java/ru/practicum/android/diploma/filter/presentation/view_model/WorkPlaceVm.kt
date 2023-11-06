package ru.practicum.android.diploma.filter.presentation.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.filter.domain.interfaces.FiltersController
import ru.practicum.android.diploma.filter.domain.models.AreaData

class WorkPlaceVm(filtersController: FiltersController) : ViewModel() {
    private val _countryChosen = MutableLiveData<AreaData?>(null)
    val countryChosen = _countryChosen as LiveData<AreaData?>

    private val _districtChosen = MutableLiveData<AreaData?>(null)
    val districtChosen = _districtChosen as LiveData<AreaData?>

    private val _acceptChanges = MutableLiveData<Boolean>(false)
    val acceptChanges = _acceptChanges as LiveData<Boolean>

    private val _errorMsg = MutableLiveData<String>()
    val errorMsg = _errorMsg as LiveData<String>

    fun chooseAnotherCountry(newCountryId: Int?, newCountryName: String?) {
        if (newCountryId != null && newCountryName != null) {
            _countryChosen.value = AreaData(newCountryId, null, newCountryName, emptyList())
        } else _countryChosen.value = null

        checkAcceptCondition()
    }

    fun chooseAnotherDistrict(newCountryId: Int?, newCountryName: String?) {
        if (newCountryId != null && newCountryName != null) {
            _districtChosen.value = AreaData(newCountryId, null, newCountryName, emptyList())
        } else _districtChosen.value = null

        checkAcceptCondition()
    }

    private fun checkAcceptCondition(){
        _acceptChanges.value = _countryChosen.value!=null &&  _districtChosen.value!=null
    }
}