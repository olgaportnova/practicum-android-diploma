package ru.practicum.android.diploma.filter.presentation.view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.handleCoroutineException
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.filter.data.impl.AreaRepositoryImpl
import ru.practicum.android.diploma.filter.domain.impl.AreaControllerImpl
import ru.practicum.android.diploma.filter.domain.models.Area
import ru.practicum.android.diploma.filter.domain.models.Country
import ru.practicum.android.diploma.filter.network.RetrofitClient
import ru.practicum.android.diploma.util.DataResource

class WorkPlaceVm:ViewModel() {
    private val _countryChosen = MutableLiveData<Country>()
    val countryChosen = _countryChosen as LiveData<Country>

    private val _districtChosen = MutableLiveData<Area>()
    val districtChosen = _districtChosen as LiveData<Area>

    private val _errorMsg = MutableLiveData<String>()
    val errorMsg = _errorMsg as LiveData<String>

    private val useCaseAreaController = AreaControllerImpl(AreaRepositoryImpl(RetrofitClient()))

    init {

        chooseAnotherCountry(Country("Russia",-25,null, emptyList()))
        chooseAnotherDistrict(Area(26,null,"district 9", emptyList()))

        //TODO: try retrofit
        loadCountryList()
    }

    private fun loadCountryList(){

        val errorHandler = CoroutineExceptionHandler { _, throwable ->
            _errorMsg.postValue(throwable.message)
        }

        viewModelScope.launch(Dispatchers.IO+errorHandler){
            useCaseAreaController.loadCountries()
        }
    }

    fun chooseAnotherCountry(newCountry: Country){
        _countryChosen.value = newCountry
    }

    fun chooseAnotherDistrict(newDistrict:Area){
        _districtChosen.value = newDistrict
    }
}