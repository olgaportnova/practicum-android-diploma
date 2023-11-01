package ru.practicum.android.diploma.filter.presentation.view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.filter.data.impl.AreaRepositoryImpl
import ru.practicum.android.diploma.filter.domain.impl.AreaControllerImpl
import ru.practicum.android.diploma.filter.domain.models.Area
import ru.practicum.android.diploma.filter.domain.models.Country
import ru.practicum.android.diploma.filter.network.DataStatus
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

        chooseAnotherCountry(Country("Russia", -25, emptyList()))
        chooseAnotherDistrict(Area(26, null, "district 9", emptyList()))

        //TODO: try retrofit
        loadCountryList()
    }

    private fun loadCountryList() {
        val errorHandler = CoroutineExceptionHandler { _, throwable ->
            _errorMsg.postValue(throwable.message)
        }
        viewModelScope.launch {
            useCaseAreaController.loadCountries().collect {
                when (it) {
                    is DataStatus.Loading -> {
                        Log.e("LOG", "LOADING")
                    }

                    is DataStatus.Content -> {
                        Log.e("LOG", "Content")
                        it.data?.forEach { country ->
                            Log.e("LOG", country.name)
                        }
                    }

                    else -> {}
                }
            }
        }
    }

    fun chooseAnotherCountry(newCountry: Country) {
        _countryChosen.value = newCountry
    }

    fun chooseAnotherDistrict(newDistrict: Area) {
        _districtChosen.value = newDistrict
    }
}