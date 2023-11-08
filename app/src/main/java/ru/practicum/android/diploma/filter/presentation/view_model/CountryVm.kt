package ru.practicum.android.diploma.filter.presentation.view_model

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.filter.domain.interfaces.AreaController
import ru.practicum.android.diploma.filter.presentation.util.DefaultViewModel
import ru.practicum.android.diploma.filter.presentation.util.ScreenState
import ru.practicum.android.diploma.util.DataStatus

class CountryVm(private val areaController: AreaController) : DefaultViewModel() {

    init {
        loadCountryList()
    }

    fun loadCountryList() {
        viewModelScope.launch {
            //Данные названия не менял на getAreas и getDistricts соответственно. Можно поменять.

            areaController.loadCountries().collect {
                when (it) {
                    is DataStatus.Loading -> _screenState.value = ScreenState.Loading(null)
                    is DataStatus.Content -> {
                        val countryList = it.data!!.map { area -> areaToAbstract(area) }
                        changeRecyclerContent(countryList)
                    }
                    else -> {}
                }
            }
        }
    }
}