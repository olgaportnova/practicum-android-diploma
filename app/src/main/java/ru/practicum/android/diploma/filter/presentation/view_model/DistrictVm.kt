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

    private val areaFullList = mutableListOf<AreaData>()

    fun loadDistrictList(parentAreaId: Int) {
        viewModelScope.launch {
            areaController.loadDistricts(parentId = parentAreaId).collect {
                if (it is DataStatus.Loading) _screenState.value = ScreenState.Loading(parentAreaId)
                if (it is DataStatus.Error) _screenState.value =
                    ScreenState.Error(errorMsg.toString())
                if (it is DataStatus.EmptyContent) _screenState.value =
                    ScreenState.EmptyContent(null)
                if (it is DataStatus.NoConnecting) _screenState.value = ScreenState.Error(null)
                if (it is DataStatus.Content) loadAllCityList(it.data!!)
            }
        }
    }

    fun loadAreaTree() {
        viewModelScope.launch {
            areaController.loadAreaTree().collect {
                if (it is DataStatus.Loading) _screenState.value = ScreenState.Loading(null)
                if (it is DataStatus.Content) listAreaTree(it.data!!)
                if (it is DataStatus.EmptyContent) _screenState.value =
                    ScreenState.EmptyContent(null)
                if (it is DataStatus.NoConnecting) _screenState.value = ScreenState.Error(null)
            }
        }
    }

    private fun listAreaTree(lstAreas: List<AreaData>) {
        fullDataList.clear()
        areaFullList.addAll(lstAreas)

        viewModelScope.launch(Dispatchers.IO) {
            lstAreas.forEach {
                findCityRecursive(it, it.id)
                areaFullList.add(it)
            }

            // Отправляем итоговый лист в recycler
            changeRecyclerContent(fullDataList)
        }

    }

    private fun loadAllCityList(parentArea: AreaData) {
        fullDataList.clear()
        viewModelScope.launch(Dispatchers.IO) {
            // Рекурсивно заполняем лист городами
            findCityRecursive(parentArea, null)

            // Отправляем итоговый лист в recycler
            changeRecyclerContent(fullDataList)
        }
    }

    private fun findCityRecursive(area: AreaData, parentId: Int?) {
        if (area.areas.isEmpty()) {

            // Прокидываем id родительского региона
            val lightArea = areaToAbstract(area).copy(parentId = parentId)

            // Добавляем регион в список для отображения в recycler
            fullDataList.add(lightArea)

        } else area.areas.forEach { findCityRecursive(it, parentId) }
    }

    fun getParentName(id: Int?): String {
        val el = areaFullList.filter { area ->
            area.id == id
        }
        return el.first().name
    }
}
