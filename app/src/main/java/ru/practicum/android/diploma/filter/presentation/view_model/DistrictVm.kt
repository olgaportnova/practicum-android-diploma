package ru.practicum.android.diploma.filter.presentation.view_model

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.filter.domain.interfaces.AreaController
import ru.practicum.android.diploma.filter.domain.models.Area
import ru.practicum.android.diploma.filter.presentation.fragment.AREA_ID
import ru.practicum.android.diploma.filter.presentation.fragment.AREA_NAME
import ru.practicum.android.diploma.filter.presentation.fragment.DistrictScreenState
import ru.practicum.android.diploma.util.DataStatus

open class DistrictVm(private val useCaseAreaController: AreaController) : ViewModel() {
    private val _errorMsg = MutableLiveData<String>()
    val errorMsg = _errorMsg as LiveData<String>

    private val _screenState =
        MutableStateFlow<DistrictScreenState>(DistrictScreenState.Loading(null))
    val screenState = _screenState as StateFlow<DistrictScreenState>

    private val cityList = mutableListOf<Area>()

    // Параметр для передачи данных от фрагментов Country и District к фрагменту WorkPlace
    // Так как передать надо только id и name, то в целях сокращения размера передаваемых данных,
    // обязательно необходимо параметр areas обнулять
    var areaToSendBack: Area? = null

    //private val useCaseAreaController = AreaControllerImpl(AreaRepositoryImpl(NetworkClientImpl()))

    /**
     * Function transform [Area] into a pair of id:[Int] and name:[String]
     * All additional info deleted from returned object
     * @return [Bundle]
     */
    fun getAreaBundle(): Bundle? {
        areaToSendBack?.let {
            val bundle = Bundle().apply {
                putInt(AREA_ID, it.id)
                putString(AREA_NAME, it.name)
            }
            return bundle
        }
        return null
    }

    fun loadCountryList() {
        viewModelScope.launch {
            useCaseAreaController.loadCountries().collect {
                when (it) {
                    is DataStatus.Loading -> _screenState.value = DistrictScreenState.Loading(null)
                    is DataStatus.Content -> _screenState.value =
                        DistrictScreenState.Content(it.data!!)

                    else -> {}
                }
            }
        }
    }

    fun loadDistrictList(parentAreaId: Int) {
        viewModelScope.launch {
            useCaseAreaController.loadDistricts(parentId = parentAreaId).collect {
                if (it is DataStatus.Loading) _screenState.value = DistrictScreenState.Loading(null)
                if (it is DataStatus.Content) loadAllCityList(it.data!!)
            }
        }
    }

    private fun loadAllCityList(parentArea: Area) {
        cityList.clear()
        viewModelScope.launch(Dispatchers.IO) {
            findCityRecursive(parentArea)
            _screenState.value = DistrictScreenState.Content(cityList)
        }
    }

    private fun findCityRecursive(area: Area) {
        if (area.areas.isEmpty()) cityList.add(area)
        else area.areas.forEach { findCityRecursive(it) }
    }
}
