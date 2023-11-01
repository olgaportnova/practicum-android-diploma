package ru.practicum.android.diploma.filter.presentation.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.filter.data.impl.AreaRepositoryImpl
import ru.practicum.android.diploma.filter.domain.impl.AreaControllerImpl
import ru.practicum.android.diploma.filter.network.DataStatus
import ru.practicum.android.diploma.filter.network.RetrofitClient
import ru.practicum.android.diploma.filter.presentation.fragment.DistrictScreenState

class CountryVm : ViewModel() {

    private val _errorMsg = MutableLiveData<String>()
    val errorMsg = _errorMsg as LiveData<String>

    private val _screenState =
        MutableStateFlow<DistrictScreenState>(DistrictScreenState.Loading(null))
    val screenState = _screenState as StateFlow<DistrictScreenState>

    private val useCaseAreaController = AreaControllerImpl(AreaRepositoryImpl(RetrofitClient()))

    init {
        //TODO: try retrofit
        loadCountryList()
    }

    private fun loadCountryList() {
        viewModelScope.launch {
            useCaseAreaController.loadCountries().collect {
                when (it) {
                    is DataStatus.Loading -> _screenState.value = DistrictScreenState.Loading(null)
                    is DataStatus.Content -> {
                        it.data?.let { list ->
                            _screenState.value = DistrictScreenState.Success(list)
                        }
                    }

                    else -> {}
                }
            }
        }
    }
}