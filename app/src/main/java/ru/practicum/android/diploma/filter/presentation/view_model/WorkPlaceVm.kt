package ru.practicum.android.diploma.filter.presentation.view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.BuildConfig
import ru.practicum.android.diploma.filter.domain.models.Area

class WorkPlaceVm : ViewModel() {
    private val _countryChosen = MutableLiveData<Area>()
    val countryChosen = _countryChosen as LiveData<Area>

    private val _districtChosen = MutableLiveData<Area>()
    val districtChosen = _districtChosen as LiveData<Area>

    private val _errorMsg = MutableLiveData<String>()
    val errorMsg = _errorMsg as LiveData<String>

    init {
        val token = BuildConfig.HH_ACCESS_TOKEN
        Log.e("LOG",token)
    }

    fun chooseAnotherCountry(newCountryId: Int?,newCountryName:String?) {
        if(newCountryId!=null&&newCountryName!=null){
            _countryChosen.value = Area(newCountryId,null,newCountryName, emptyList())
        }
    }

    fun chooseAnotherDistrict(newCountryId: Int?,newCountryName:String?) {
        if(newCountryId!=null&&newCountryName!=null){
            _districtChosen.value = Area(newCountryId,null,newCountryName, emptyList())
        }
    }
}