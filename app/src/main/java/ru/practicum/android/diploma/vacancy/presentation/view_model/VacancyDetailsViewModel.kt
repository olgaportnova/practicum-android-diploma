package ru.practicum.android.diploma.vacancy.presentation.view_model

import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.search.domain.models.Vacancy
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
        if (vacancyId.isNotEmpty()) {
            //_screenState.value = VacancyDetailsScreenState.Loading

            viewModelScope.launch {
                vacancyDetailsInteractor
                    .getVacancyDetails(vacancyId)
                    .collect { pair ->
                        processResult(pair.result, pair.error)
                    }
            }
        }
    }

    /*fun getVacancyDetails(vacancyId: String) {
        viewModelScope.launch {
            vacancyDetailsInteractor.getVacancyDetails(vacancyId).collect {
                if (it is DataStatus.Loading) _screenState.value = VacancyDetailsScreenState.Loading
                if (it is DataStatus.Content) _screenState.value = VacancyDetailsScreenState.Content(it.data!!)
            }
        }
    }*/

    private fun processResult(foundVacancy: Vacancy?, errorMessage: Int?) {

        when {
            errorMessage != null -> {
                _screenState.value =
                    VacancyDetailsScreenState.Error(
                        errorMessage = application.getString(
                            R.string.server_error
                        )
                    )
            }

            else -> {
                _screenState.value = VacancyDetailsScreenState.Content(foundVacancy = foundVacancy)
                Log.d("VAC found", foundVacancy.toString())
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

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
    }
}