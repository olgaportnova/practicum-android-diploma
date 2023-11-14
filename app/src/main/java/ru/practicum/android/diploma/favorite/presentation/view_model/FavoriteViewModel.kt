package ru.practicum.android.diploma.favorite.presentation.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.favorite.domain.FavoriteInteractor
import ru.practicum.android.diploma.favorite.domain.FavoriteState

class FavoriteViewModel(
    private val favoriteInteractor: FavoriteInteractor
) : ViewModel() {

    private val _screenState = MutableStateFlow<FavoriteState>(FavoriteState.EmptyList)
    val screenState = _screenState as StateFlow<FavoriteState>
    fun getAllFavoriteVacancies() {
        viewModelScope.launch {
            try {
                favoriteInteractor.getAllFavouriteVacancies().collect { vacancies ->
                    if (vacancies.isEmpty()) {
                        _screenState.value = FavoriteState.EmptyList
                    } else {
                        _screenState.value = FavoriteState.Success(vacancies)
                    }
                }
            } catch (e: Exception) {
                _screenState.value = FavoriteState.Error
            }
        }
    }

}