package ru.practicum.android.diploma.filter.presentation.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.filter.domain.models.Area
import ru.practicum.android.diploma.filter.domain.models.Country

class WorkPlaceVm:ViewModel() {
    private val _countryChosen = MutableLiveData<Country>()
    val countryChosen = _countryChosen as LiveData<Country>

    private val _districtChosen = MutableLiveData<Area>()
    val districtChosen = _districtChosen as LiveData<Area>

    init {
        chooseAnotherCountry(Country("Any",-25,null, emptyList()))
        chooseAnotherDistrict(Area(26,null,"district 9", emptyList()))
    }

    fun chooseAnotherCountry(newCountry: Country){
        _countryChosen.value = newCountry
    }

    fun chooseAnotherDistrict(newDistrict:Area){
        _districtChosen.value = newDistrict
    }
}