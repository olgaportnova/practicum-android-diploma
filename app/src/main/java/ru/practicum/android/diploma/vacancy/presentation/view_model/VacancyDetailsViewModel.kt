package ru.practicum.android.diploma.vacancy.presentation.view_model

import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.util.DataStatus
import ru.practicum.android.diploma.vacancy.domain.interactor.VacancyDetailsInteractor
import ru.practicum.android.diploma.vacancy.domain.models.VacancyDetailsScreenState

class VacancyDetailsViewModel(
    private val application: Application,
    private val vacancyDetailsInteractor: VacancyDetailsInteractor
) : ViewModel() {

    private val _screenState =
        MutableStateFlow<VacancyDetailsScreenState>(VacancyDetailsScreenState.Loading)
    val screenState = _screenState as StateFlow<VacancyDetailsScreenState>

    fun getVacancyDetails(vacancyId: String) {
        viewModelScope.launch {
            vacancyDetailsInteractor.getVacancyDetails(vacancyId).collect {
                when (it) {
                    is DataStatus.Loading -> _screenState.value = VacancyDetailsScreenState.Loading

                    is DataStatus.Content -> {
                        _screenState.value =
                            VacancyDetailsScreenState.Content(it.data!!)
                        Log.d("VAC found", it.data.toString())
                    }

                    is DataStatus.NoConnecting -> _screenState.value = VacancyDetailsScreenState.Error

                    is DataStatus.Error -> _screenState.value = VacancyDetailsScreenState.Error

                    else -> {}
                }
            }
        }
    }

    /*fun shareVacancy(vacancyUrl: String) {
        sharingInteractor.shareVacancy(vacancyUrl)
    }

    fun onFavoriteClicked(vacancy: Vacancy) {
        viewModelScope.launch {
            val newFavoriteStatus = if (!vacancy.isFavorite) {
                favoriteInteractor.insertVacancyToFavoriteList(vacancy)
                true
            } else {
                favoriteInteractor.deleteVacancyFromFavoriteList(vacancy)
                false
            }

            _screenState.value = newFavoriteStatus

        }
    }

    suspend fun isVacancyFavorite(vacancyId: String): Boolean {
        favoriteInteractor.doesVacancyInFavoriteList()
    }*/

}