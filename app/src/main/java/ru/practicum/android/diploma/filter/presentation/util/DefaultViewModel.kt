package ru.practicum.android.diploma.filter.presentation.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ru.practicum.android.diploma.filter.domain.models.AbstractData
import ru.practicum.android.diploma.filter.domain.models.AreaData
import ru.practicum.android.diploma.filter.domain.models.RoleData

open class DefaultViewModel : ViewModel() {
    val _errorMsg = MutableLiveData<String>()
    val errorMsg = _errorMsg as LiveData<String>

    val _screenState =
        MutableStateFlow<ScreenState>(ScreenState.Loading(null))
    val screenState = _screenState as StateFlow<ScreenState>

    var dataToSendBack: AbstractData? = null

    private var _itemPosToUpdate = MutableLiveData<Int>()
    val itemPosToUpdate = _itemPosToUpdate as LiveData<Int>

    private var previouslySelectedItemPos: Int? = null

    val fullDataList = mutableListOf<AbstractData>()

    fun areaToAbstract(area: AreaData) = AbstractData(area.id, area.name)

    fun roleToAbstract(role: RoleData) = AbstractData(role.id, role.name)

    fun selectItemInDataList(selectedData: AbstractData) {
        if (fullDataList.isNotEmpty()) {
            val selectedItemIndex = fullDataList.indexOf(selectedData) // Поиск позиции в списке
            if (selectedItemIndex != -1) {
                // Снимаем выделение с предыдущего выделенного элемента
                previouslySelectedItemPos?.let {
                    if (it < fullDataList.size) fullDataList[it].isSelected = false
                    _itemPosToUpdate.value = it
                }

                // Устанавливаем выделение на выбранный элемент
                fullDataList[selectedItemIndex].isSelected = true
                _itemPosToUpdate.value = selectedItemIndex

                previouslySelectedItemPos = selectedItemIndex
            }
        }
    }

    fun searchInputData(inputText: CharSequence?): List<AbstractData> {
        return if (inputText.isNullOrBlank()) {
            fullDataList
        } else {
            fullDataList.filter {
                it.name.contains(other = inputText, ignoreCase = true)
            }
        }
    }

}