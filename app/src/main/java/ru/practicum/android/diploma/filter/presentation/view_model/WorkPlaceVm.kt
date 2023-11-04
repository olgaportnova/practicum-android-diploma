package ru.practicum.android.diploma.filter.presentation.view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.BuildConfig
import ru.practicum.android.diploma.filter.domain.models.Area
import ru.practicum.android.diploma.filter.network.RetrofitClient

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

        val retro = RetrofitClient()
        viewModelScope.launch {
            val response = retro.loadVacanciesByArea()
            when(response.code()){
                200-> {
                    Log.e("LOG", "200")
                    response.body()?.let {
                        Log.e("LOG", "found ${it.found} ")
                    }
                }
                else->{
                    Log.e("LOG","${response.code()}")
                }
            }
        }
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