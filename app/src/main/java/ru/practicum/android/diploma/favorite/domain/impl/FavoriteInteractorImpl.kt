package ru.practicum.android.diploma.favorite.domain.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.favorite.domain.FavoriteInteractor
import ru.practicum.android.diploma.favorite.domain.FavoriteRepository
import ru.practicum.android.diploma.search.domain.models.Vacancy
import ru.practicum.android.diploma.search.domain.models.VacancyForTests

class FavoriteInteractorImpl(
    private val favoriteRepository: FavoriteRepository
): FavoriteInteractor {
    override suspend fun insertVacancyToFavoriteList(vacancy: VacancyForTests) {
        favoriteRepository.insertVacancyToFavoriteList(vacancy)
    }

    override suspend fun deleteVacancyFromFavoriteList(vacancy: VacancyForTests) {
        favoriteRepository.deleteVacancyFromFavoriteList(vacancy)
    }

    override fun getAllFavouriteVacancies(): Flow<List<VacancyForTests>> {
       return favoriteRepository.getAllFavouriteVacancies()
    }

    override suspend fun getFavouriteVacancyById(id: Int): VacancyForTests? {
        return favoriteRepository.getFavouriteVacancyById(id)
    }

    override suspend fun doesVacancyInFavoriteList(id: Int): Boolean {
        return favoriteRepository.doesVacancyInFavoriteList(id)
    }

}