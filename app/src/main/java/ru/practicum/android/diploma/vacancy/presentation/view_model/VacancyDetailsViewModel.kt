package ru.practicum.android.diploma.vacancy.presentation.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.favorite.domain.FavoriteInteractor
import ru.practicum.android.diploma.search.domain.models.Vacancy
import ru.practicum.android.diploma.util.DataStatus
import ru.practicum.android.diploma.vacancy.domain.interactor.VacancyDetailsInteractor
import ru.practicum.android.diploma.vacancy.domain.models.VacancyDetailsScreenState

class VacancyDetailsViewModel(
    private val vacancyDetailsInteractor: VacancyDetailsInteractor,
    private val favoriteInteractor: FavoriteInteractor,
) : ViewModel() {

    private val _screenState =
        MutableStateFlow<VacancyDetailsScreenState>(VacancyDetailsScreenState.Loading)
    val screenState = _screenState as StateFlow<VacancyDetailsScreenState>

    private val _currentVacancy = MutableLiveData<Vacancy?>(null)
    val currentVacancy = _currentVacancy as LiveData<Vacancy?>

    fun getVacancyDetails(vacancyId: String) {
        viewModelScope.launch {
            vacancyDetailsInteractor.getVacancyDetails(vacancyId).collect {
                when (it) {
                    is DataStatus.Loading -> _screenState.value = VacancyDetailsScreenState.Loading

                    is DataStatus.Content -> {
                        _currentVacancy.value = it.data
                        val isFavorite = isVacancyFavorite(vacancyId)
                        _screenState.value =
                            VacancyDetailsScreenState.SimilarVacanciesButtonState(true)
                        _screenState.value =
                            VacancyDetailsScreenState.Content(it.data!!, isFavorite)
                    }

                    is DataStatus.NoConnecting -> {
                        if (favoriteInteractor.doesVacancyInFavoriteList(vacancyId.toInt())) {
                            getVacancyFromDb(vacancyId.toInt())
                            _screenState.value =
                                VacancyDetailsScreenState.SimilarVacanciesButtonState(false)
                        } else
                            _screenState.value =
                                VacancyDetailsScreenState.Error
                    }

                    is DataStatus.Error -> _screenState.value = VacancyDetailsScreenState.Error

                    else -> {}
                }
            }
        }
    }

    private fun getVacancyFromDb(vacancyId: Int) {
        viewModelScope.launch {
            val vacancyFromDb = favoriteInteractor.getFavouriteVacancyById(vacancyId)
            _screenState.value =
                VacancyDetailsScreenState.Content(
                    foundVacancy = vacancyFromDb,
                    favoriteStatus = true
                )
        }
    }

    private suspend fun isVacancyFavorite(vacancyId: String): Boolean {
        return favoriteInteractor.doesVacancyInFavoriteList(vacancyId.toInt())
    }

    fun onFavoriteClicked(vacancy: Vacancy) {

        viewModelScope.launch {
            val vacancyId = vacancy.id
            val favoriteStatus = isVacancyFavorite(vacancyId.toString())

            if (favoriteStatus) {
                vacancy.let { favoriteInteractor.deleteVacancyFromFavoriteList(it) }
                _screenState.value = VacancyDetailsScreenState.Content(
                    foundVacancy = vacancy,
                    favoriteStatus = false
                )

            } else {
                vacancy.let { favoriteInteractor.insertVacancyToFavoriteList(it) }
                _screenState.value =
                    VacancyDetailsScreenState.Content(foundVacancy = vacancy, favoriteStatus = true)
            }

        }
    }

    /*fun shareVacancy(vacancyUrl: String) {
        sharingInteractor.shareVacancy(vacancyUrl)
    }*/

}