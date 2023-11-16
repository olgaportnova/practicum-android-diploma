package ru.practicum.android.diploma.filter.presentation.view_model

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.filter.domain.interfaces.IndustriesController
import ru.practicum.android.diploma.filter.domain.models.AbstractData
import ru.practicum.android.diploma.filter.domain.models.CategoryData
import ru.practicum.android.diploma.filter.presentation.util.DefaultViewModel
import ru.practicum.android.diploma.filter.presentation.util.ScreenState
import ru.practicum.android.diploma.util.DataStatus

class IndustryVm(private val industriesController: IndustriesController) : DefaultViewModel() {
    private var preselectedIndustryId: Int? = null

    init {
        loadIndustries()
    }

    private fun loadIndustries() {
        viewModelScope.launch {
            industriesController.getIndustries().collect {
                when (it) {
                    is DataStatus.Loading -> _screenState.value = ScreenState.Loading(null)
                    is DataStatus.Content -> loadAllRoles(it.data!!)
                    is DataStatus.EmptyContent -> _screenState.value =
                        ScreenState.EmptyContent(null)

                    is DataStatus.Error -> _screenState.value =
                        ScreenState.Error(errorMsg.toString())

                    is DataStatus.NoConnecting -> _screenState.value = ScreenState.Error(null)
                    else -> {}
                }

            }
        }
    }

    private fun loadAllRoles(categories: List<CategoryData>) {
        fullDataList.clear()

        viewModelScope.launch(Dispatchers.IO) {
            categories.forEach {
                fullDataList.add(AbstractData(it.id, it.name))
            }

            // Если при заходе на экран профессия уже была выбрана,
            // Получаем элемент списка профессий с заданным id
            val preselectedIndustry = fullDataList.filter {
                it.id == preselectedIndustryId
            }

            // Если элемент был найден, выделяем его в RecyclerView
            if (preselectedIndustry.isNotEmpty()) {
                selectItemInDataList(preselectedIndustry.first())
            }

            _screenState.value = ScreenState.Content(fullDataList)
        }
    }

    fun setPreselectedIndustryId(remoteIndustryId: Int?) {
        this.preselectedIndustryId = remoteIndustryId
    }
}