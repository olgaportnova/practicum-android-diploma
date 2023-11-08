package ru.practicum.android.diploma.filter.presentation.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.filter.domain.models.AbstractData
import ru.practicum.android.diploma.filter.domain.models.AreaData
import ru.practicum.android.diploma.filter.domain.models.RoleData

open class DefaultViewModel : ViewModel() {
    protected val mutableErrorMsg = MutableLiveData<String>()
    val errorMsg = mutableErrorMsg as LiveData<String>

    protected val _screenState = MutableStateFlow<ScreenState>(ScreenState.Loading(null))
    val screenState = _screenState as StateFlow<ScreenState>

    var dataToSendBack: AbstractData? = null

    private var _itemPosToUpdate = MutableLiveData<Int>()
    val itemPosToUpdate = _itemPosToUpdate as LiveData<Int>

    private var previouslySelectedItemPos: AbstractData? = null

    protected val fullDataList = mutableListOf<AbstractData>()

    protected var searchInputText = String()

    fun areaToAbstract(area: AreaData) = AbstractData(area.id, area.name)

    fun roleToAbstract(role: RoleData) = AbstractData(role.id, role.name)

    fun selectItemInDataList(selectedData: AbstractData) {
        //val activeList = searchInputData(searchInputText)
        viewModelScope.launch {
            val activeList = asyncSearchInputText(searchInputText).await()

            if (activeList.isNotEmpty()) {
                val selectedItemIndex = activeList.indexOf(selectedData) // Поиск позиции в списке
                if (selectedItemIndex != -1) {
                    // Снимаем выделение с предыдущего выделенного элемента
                    previouslySelectedItemPos?.let {
                        val prevSelItemPos = activeList.indexOf(it)
                        activeList[prevSelItemPos].isSelected = false
                        _itemPosToUpdate.value = prevSelItemPos
                    }

                    // Устанавливаем выделение на выбранный элемент
                    activeList[selectedItemIndex].isSelected = true
                    _itemPosToUpdate.value = selectedItemIndex

                    previouslySelectedItemPos = selectedData
                }
            }
        }
    }

    fun searchInputData(inputText: CharSequence?) {
        this.searchInputText = inputText.toString()
        if (inputText.isNullOrBlank()) {
            changeRecyclerContent(fullDataList)
        } else {
            viewModelScope.launch {
                changeRecyclerContent(fullDataList.filter {
                    it.name.contains(other = inputText, ignoreCase = true)
                })
            }
        }
    }

    protected fun changeRecyclerContent(list: List<AbstractData>) {
        if (list.isEmpty()) _screenState.value = ScreenState.EmptyContent(null)
        else _screenState.value = ScreenState.Content(list)
    }

    suspend fun asyncSearchInputText(inputText: CharSequence?): Deferred<List<AbstractData>> =
        viewModelScope.async {
            searchInputText = inputText.toString()
            return@async if (inputText.isNullOrBlank()) {
                fullDataList
            } else {
                fullDataList.filter {
                    it.name.contains(other = inputText, ignoreCase = true)
                }
            }
        }
}

