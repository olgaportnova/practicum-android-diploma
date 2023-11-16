package ru.practicum.android.diploma.filter.presentation.view_model

import android.util.Log
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.filter.domain.interfaces.IndustriesController
import ru.practicum.android.diploma.filter.domain.models.AbstractData
import ru.practicum.android.diploma.filter.domain.models.CategoryData
import ru.practicum.android.diploma.filter.presentation.util.DefaultViewModel
import ru.practicum.android.diploma.filter.presentation.util.ScreenState
import ru.practicum.android.diploma.util.DataStatus

class IndustryVm(private val industriesController: IndustriesController) : DefaultViewModel() {

    init {
        loadIndustries()
    }

    private fun loadIndustries() {
        viewModelScope.launch {
            industriesController.getIndustries().collect {
                Log.d("status", it.toString())
                when (it) {
                    is DataStatus.Loading -> _screenState.value = ScreenState.Loading(null)
                    is DataStatus.Content -> loadAllRoles(it.data!!)
                    is DataStatus.EmptyContent -> _screenState.value = ScreenState.EmptyContent(R.string.no_such_industry)
                    is DataStatus.Error -> _screenState.value = ScreenState.Error(errorMsg.toString())
                    is DataStatus.NoConnecting -> _screenState.value = ScreenState.Error(errorMsg.toString())
                    else -> {}
                }
            }
        }
    }

    private fun loadAllRoles(categories: List<CategoryData>) {
        fullDataList.clear()

        viewModelScope.launch(Dispatchers.IO) {
            categories.forEach {
                fullDataList.add(AbstractData(it.id,it.name))
                //it.roles.forEach { role -> fullDataList.add(roleToAbstract(role)) }
            }

            _screenState.value = ScreenState.Content(fullDataList)
        }
    }
}