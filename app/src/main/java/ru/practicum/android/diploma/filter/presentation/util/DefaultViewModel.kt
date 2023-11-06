package ru.practicum.android.diploma.filter.presentation.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ru.practicum.android.diploma.filter.domain.models.AbstarctData
import ru.practicum.android.diploma.filter.domain.models.AreaData
import ru.practicum.android.diploma.filter.domain.models.RoleData

open class DefaultViewModel : ViewModel() {
    val _errorMsg = MutableLiveData<String>()
    val errorMsg = _errorMsg as LiveData<String>

    val _screenState =
        MutableStateFlow<ScreenState>(ScreenState.Loading(null))
    val screenState = _screenState as StateFlow<ScreenState>

    var dataToSendBack: AbstarctData? = null

    protected val fullDataList = mutableListOf<AbstarctData>()

    fun areaToAbstract(area: AreaData) = AbstarctData(area.id, area.name)

    fun roleToAbstract(role: RoleData) = AbstarctData(role.id, role.name)

    fun searchInputData(inputText:CharSequence?):List<AbstarctData>{
        return if (inputText.isNullOrBlank()){
            fullDataList
        } else {
            fullDataList.filter {
                it.name.contains(other = inputText, ignoreCase = true)
            }
        }
    }

}