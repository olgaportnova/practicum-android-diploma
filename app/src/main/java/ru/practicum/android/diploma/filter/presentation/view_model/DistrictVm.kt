package ru.practicum.android.diploma.filter.presentation.view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.filter.data.impl.AreaRepositoryImpl
import ru.practicum.android.diploma.filter.domain.impl.AreaControllerImpl
import ru.practicum.android.diploma.filter.domain.models.Country
import ru.practicum.android.diploma.filter.network.DataStatus
import ru.practicum.android.diploma.filter.network.RetrofitClient
import ru.practicum.android.diploma.filter.presentation.fragment.DistrictScreenState

open class DistrictVm : ViewModel() {
    private val _errorMsg = MutableLiveData<String>()
    val errorMsg = _errorMsg as LiveData<String>

    private val _screenState =
        MutableStateFlow<DistrictScreenState>(DistrictScreenState.Loading(null))
    val screenState = _screenState as StateFlow<DistrictScreenState>

    // Параметр для передачи данных от фрагментов Country и District к фрагменту WorkPlace
    // Так как передать надо только id и name, то в целях сокращения размера передаваемых данных,
    // выбран тип Country (без вложенных массивов данных)
    var areaToSendBack: Country = Country()

    private val useCaseAreaController = AreaControllerImpl(AreaRepositoryImpl(RetrofitClient()))

    fun loadCountryList() {
        viewModelScope.launch {
            useCaseAreaController.loadCountries().collect {
                when (it) {
                    is DataStatus.Loading -> _screenState.value = DistrictScreenState.Loading(null)
                    is DataStatus.Content -> {
                        it.data?.let { list ->
                            _screenState.value = DistrictScreenState.ContentCountry(list)
                        }
                    }

                    else -> {}
                }
            }
        }
    }

    fun loadDistrictList(parentAreaId:Int){
        viewModelScope.launch {
            useCaseAreaController.loadDistricts(parentId = parentAreaId).collect{
                if (it is DataStatus.Loading) _screenState.value = DistrictScreenState.Loading(null)
                if(it is DataStatus.Content) {
                    it.data?.let {area->
                        _screenState.value = DistrictScreenState.ContentDistrict(area.areas)
                    }
                }
            }
        }
    }
}