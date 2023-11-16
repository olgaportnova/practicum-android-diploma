package ru.practicum.android.diploma.filter.presentation.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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

    private var searchInputText = String()

    private var searchJob: Job? = null

    fun areaToAbstract(area: AreaData) = AbstractData(area.id, area.name)

    fun selectItemInDataList(selectedData: AbstractData) {
        viewModelScope.launch(Dispatchers.IO) {
            // Сравнение производим со списком в recycler для избежания ситуации, когда после выделения
            // пользователь ввел фильтрующий запрос, и список был изменен
            val activeList = fullDataList

            if (activeList.isNotEmpty()) {
                // Поиск позиции в списке
                val selectedItemIndex = activeList.indexOfFirst { it == selectedData }

                if (selectedItemIndex != -1) {
                    // Если выбранный элемент найден в листе,
                    // снимаем выделение с предыдущего выделенного элемента
                    previouslySelectedItemPos?.let {
                        // Если до этого существовал выделенный элемент, ищем его в обновленном списке
                        val prevSelItemPos = activeList.indexOfFirst { data -> data == it }

                        // Если элемент присутствует в списке, снимаем выделение
                        if (prevSelItemPos != -1) {
                            activeList[prevSelItemPos].isSelected = false
                            withContext(Dispatchers.Main){
                                _itemPosToUpdate.value = prevSelItemPos
                            }

                        }
                    }

                    // Устанавливаем выделение на выбранный элемент
                    activeList[selectedItemIndex].apply {
                        isSelected = true
                        previouslySelectedItemPos = this
                    }
                    withContext(Dispatchers.Main) {
                        _itemPosToUpdate.value = selectedItemIndex
                    }

                }
            }
        }
    }

    fun txtSearchChanged(inputText: CharSequence?) {
        this.searchInputText = inputText.toString()
        searchJob?.cancel()
        if(_screenState.value!=ScreenState.Error(null)) {
            startSearching()
        }
    }


    private fun searchInputData(inputText: CharSequence?) {
        if (inputText.isNullOrBlank()) {
            changeRecyclerContent(fullDataList)
        } else {
            viewModelScope.launch {
                val filteredList = withContext(Dispatchers.Default) {
                    fullDataList.filter { it.name.contains(other = inputText, ignoreCase = true) }
                }
                changeRecyclerContent(filteredList)
            }
        }
    }

    private fun startSearching() {
        searchJob = viewModelScope.launch {
            delay(SEARCH_DELAY_mills)
            searchInputData(searchInputText)
        }
    }

    protected fun changeRecyclerContent(list: List<AbstractData>) {
        if (list.isEmpty()) _screenState.value = ScreenState.EmptyContent(null)
        else _screenState.value = ScreenState.Content(list)
    }


    companion object {
        const val SEARCH_DELAY_mills = 150L
    }
}

