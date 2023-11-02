package ru.practicum.android.diploma.filter.presentation.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.filter.domain.models.Area

class WorkPlaceVm : ViewModel() {
    private val _countryChosen = MutableLiveData<Area>()
    val countryChosen = _countryChosen as LiveData<Area>

    private val _districtChosen = MutableLiveData<Area>()
    val districtChosen = _districtChosen as LiveData<Area>

    private val _errorMsg = MutableLiveData<String>()
    val errorMsg = _errorMsg as LiveData<String>

    fun chooseAnotherCountry(newCountry: Area) {
        _countryChosen.value = newCountry
    }

    fun chooseAnotherDistrict(newDistrict: Area) {
        _districtChosen.value = newDistrict
    }
}