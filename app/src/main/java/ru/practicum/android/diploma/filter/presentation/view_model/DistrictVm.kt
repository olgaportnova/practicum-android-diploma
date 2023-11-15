package ru.practicum.android.diploma.filter.presentation.view_model

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.filter.domain.interfaces.AreaController
import ru.practicum.android.diploma.filter.domain.models.AreaData
import ru.practicum.android.diploma.filter.presentation.util.DefaultViewModel
import ru.practicum.android.diploma.filter.presentation.util.ScreenState
import ru.practicum.android.diploma.util.DataStatus

open class DistrictVm(private val areaController: AreaController) : DefaultViewModel() {
    fun loadDistrictList(parentAreaId: Int) {
        viewModelScope.launch {
            areaController.loadDistricts(parentId = parentAreaId).collect {
                if (it is DataStatus.Loading) _screenState.value = ScreenState.Loading(parentAreaId)
                if (it is DataStatus.Content) loadAllCityList(it.data!!)

                if (it is DataStatus.NoConnecting) mutableErrorMsg.value = "No Connection"
                if (it is DataStatus.EmptyContent) mutableErrorMsg.value = "Empty Content"
                if (it is DataStatus.Error) mutableErrorMsg.value = "${it.code}"
            }
        }
    }

    fun loadAreaTree() {
        viewModelScope.launch {
            areaController.loadAreaTree().collect {
                if (it is DataStatus.Loading) _screenState.value = ScreenState.Loading(null)
                if (it is DataStatus.Content) loadAllDistricts(it.data!!)

                if (it is DataStatus.NoConnecting) mutableErrorMsg.value = "No Connection"
                if (it is DataStatus.EmptyContent) mutableErrorMsg.value = "Empty Content"
                if (it is DataStatus.Error) mutableErrorMsg.value = "${it.code}"
            }
        }
    }

    private fun loadAllDistricts(lstAreas: List<AreaData>) {
        fullDataList.clear()
        viewModelScope.launch(Dispatchers.IO) {
            lstAreas.forEach {
                findCityRecursive(it)
            }

            // Отправляем итоговый лист в recycler
            changeRecyclerContent(fullDataList)
        }

    }


    private fun loadAllCityList(parentArea: AreaData) {
        fullDataList.clear()
        viewModelScope.launch(Dispatchers.IO) {
            // Рекурсивно заполняем лист городами
            findCityRecursive(parentArea)

            // Отправляем итоговый лист в recycler
            changeRecyclerContent(fullDataList)
        }
    }

    private fun findCityRecursive(area: AreaData) {
        if (area.areas.isEmpty()) fullDataList.add(areaToAbstract(area))
        else area.areas.forEach { findCityRecursive(it) }
    }
}
