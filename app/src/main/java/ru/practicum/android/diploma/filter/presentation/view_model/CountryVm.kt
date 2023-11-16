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

    private fun loadCountryList() {
        viewModelScope.launch {
            areaController.loadCountries().collect {
                when (it) {
                    is DataStatus.Loading -> _screenState.value = ScreenState.Loading(null)
                    is DataStatus.Content -> {
                        val countryList = it.data!!.map { area -> areaToAbstract(area) }
                        changeRecyclerContent(countryList)
                    }
                    is DataStatus.NoConnecting -> _screenState.value = ScreenState.Error(errorMsg.toString())
                    else -> {}
                }
            }
        }
    }
}