package ru.practicum.android.diploma.filter.presentation.view_model

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.filter.domain.interfaces.AreaController
import ru.practicum.android.diploma.filter.domain.models.AreaData
import ru.practicum.android.diploma.filter.presentation.util.ScreenState
import ru.practicum.android.diploma.util.DataStatus

open class DistrictVm(private val areaController: AreaController) : DefaultViewModel() {
    private val cityList = mutableListOf<AreaData>()



    fun loadDistrictList(parentAreaId: Int) {
        viewModelScope.launch {
            areaController.loadDistricts(parentId = parentAreaId).collect {
                if (it is DataStatus.Loading) _screenState.value = ScreenState.Loading(null)
                if (it is DataStatus.Content) loadAllCityList(it.data!!)
            }
        }
    }

    private fun loadAllCityList(parentArea: AreaData) {
        cityList.clear()
        viewModelScope.launch(Dispatchers.IO) {
            findCityRecursive(parentArea) // Рекурсивно заполняем лист городами

            _screenState.value = ScreenState.Content(cityList.map {
                areaToAbstract(it)
            })
        }
    }

    private fun findCityRecursive(area: AreaData) {
        if (area.areas.isEmpty()) cityList.add(area)
        else area.areas.forEach { findCityRecursive(it) }
    }
}
