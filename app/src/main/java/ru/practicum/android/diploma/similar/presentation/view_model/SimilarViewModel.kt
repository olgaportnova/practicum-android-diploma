package ru.practicum.android.diploma.similar.presentation.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.favorite.domain.FavoriteState
import ru.practicum.android.diploma.similar.domain.SimilarInteractor
import ru.practicum.android.diploma.util.DataStatus
class SimilarViewModel(
    private val similarInteractor: SimilarInteractor
) : ViewModel() {

    private val _screenState = MutableStateFlow<FavoriteState>(FavoriteState.Loading)
    val screenState = _screenState as StateFlow<FavoriteState>


    fun getSimilarVacancies(vacancyId:String) {
        viewModelScope.launch {
            similarInteractor.getSimilarVacancies(vacancyId).collect {
                when (it) {
                    is DataStatus.Loading -> _screenState.value = FavoriteState.Loading
                    is DataStatus.Content -> _screenState.value = FavoriteState.Success(it.data!!)
                    is DataStatus.NoConnecting -> _screenState.value = FavoriteState.NoInternet
                    is DataStatus.Error -> _screenState.value = FavoriteState.Error
                    else -> {}
                }
            }

        }
    }
}