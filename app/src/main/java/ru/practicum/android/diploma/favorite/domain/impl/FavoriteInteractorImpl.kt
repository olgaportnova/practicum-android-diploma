package ru.practicum.android.diploma.favorite.domain.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.favorite.domain.FavoriteInteractor
import ru.practicum.android.diploma.favorite.domain.FavoriteRepository
import ru.practicum.android.diploma.search.domain.models.Vacancy

class FavoriteInteractorImpl(
    private val favoriteRepository: FavoriteRepository
): FavoriteInteractor {
    override suspend fun insertVacancyToFavoriteList(vacancy: Vacancy) {
        favoriteRepository.insertVacancyToFavoriteList(vacancy)
    }

    override suspend fun deleteVacancyFromFavoriteList(vacancy: Vacancy) {
        favoriteRepository.deleteVacancyFromFavoriteList(vacancy)
    }

    override suspend fun getAllFavouriteVacancies(): Flow<List<Vacancy>> {
       return favoriteRepository.getAllFavouriteVacancies()
    }

    override suspend fun getFavouriteVacancyById(id: Int): Vacancy? {
        return favoriteRepository.getFavouriteVacancyById(id)
    }

    override suspend fun doesVacancyInFavoriteList(id: Int): Boolean {
        return favoriteRepository.doesVacancyInFavoriteList(id)
    }

}