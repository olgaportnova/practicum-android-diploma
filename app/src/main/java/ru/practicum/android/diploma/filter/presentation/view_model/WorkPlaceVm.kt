package ru.practicum.android.diploma.filter.presentation.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ru.practicum.android.diploma.filter.domain.interfaces.FiltersController
import ru.practicum.android.diploma.filter.domain.models.FilterData
import ru.practicum.android.diploma.filter.presentation.fragment.ScreenWorkPlaceState

class WorkPlaceVm(private val filtersController: FiltersController) : ViewModel() {
    private var _screenState =
        MutableStateFlow<ScreenWorkPlaceState>(ScreenWorkPlaceState.Loading(null))
    val screenState = _screenState as StateFlow<ScreenWorkPlaceState>

    private val _errorMsg = MutableLiveData<String>()
    val errorMsg = _errorMsg as LiveData<String>

    private var initFilterSet: FilterData? = null
    private var newFilterSet: FilterData = filtersController.getDefaultSettings()

    fun loadFilterSetFromSharedModel(sharedFilterSet: FilterData) {
        // initFilterSet заполняется только первый раз
        // Дальнейшие изменения затрагивают только newFilterSet
        // Для обеспечения возможности сравнения первичного сета настроек фильтрации и обновленного
        if (initFilterSet == null) initFilterSet = sharedFilterSet

        newFilterSet = sharedFilterSet

        _screenState.value = ScreenWorkPlaceState.Content(newFilterSet, checkAcceptCondition())
    }

    fun clearCountry() {
        newFilterSet = newFilterSet.copy(idCountry = null, nameCountry = null)

        _screenState.value = ScreenWorkPlaceState.Content(newFilterSet, checkAcceptCondition())
    }

    fun clearDistrict() {
        newFilterSet = newFilterSet.copy(idArea = null, nameArea = null)

        _screenState.value = ScreenWorkPlaceState.Content(newFilterSet, checkAcceptCondition())
    }

    fun getParentAreaIdToSearch() = newFilterSet.idCountry?.toIntOrNull()

    fun getUpdatedFilterSet() = this.newFilterSet

    private fun checkAcceptCondition(): Boolean {
        return newFilterSet != initFilterSet
    }

}