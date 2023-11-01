package ru.practicum.android.diploma.filter.presentation.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.filter.data.impl.AreaRepositoryImpl
import ru.practicum.android.diploma.filter.domain.impl.AreaControllerImpl
import ru.practicum.android.diploma.filter.domain.models.Area
import ru.practicum.android.diploma.filter.domain.models.Country
import ru.practicum.android.diploma.filter.network.RetrofitClient

class WorkPlaceVm : ViewModel() {
    private val _countryChosen = MutableLiveData<Country>()
    val countryChosen = _countryChosen as LiveData<Country>

    private val _districtChosen = MutableLiveData<Area>()
    val districtChosen = _districtChosen as LiveData<Area>

    private val _errorMsg = MutableLiveData<String>()
    val errorMsg = _errorMsg as LiveData<String>

    private val useCaseAreaController = AreaControllerImpl(AreaRepositoryImpl(RetrofitClient()))

    init {
        chooseAnotherCountry(Country("Страна", -25, ""))
        chooseAnotherDistrict(Area(26, null, "Регион", emptyList()))
    }

    fun chooseAnotherCountry(newCountry: Country) {
        _countryChosen.value = newCountry
    }

    fun chooseAnotherDistrict(newDistrict: Area) {
        _districtChosen.value = newDistrict
    }
}