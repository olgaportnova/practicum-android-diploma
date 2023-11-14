package ru.practicum.android.diploma.favorite.domain

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.search.domain.models.Vacancy

interface FavoriteRepository {

    suspend fun insertVacancyToFavoriteList(vacancy: Vacancy)

    suspend fun deleteVacancyFromFavoriteList(vacancy: Vacancy)

    fun getAllFavouriteVacancies(): Flow<List<Vacancy>>

    suspend fun getFavouriteVacancyById(id:Int): Vacancy?

    suspend fun doesVacancyInFavoriteList(id: Int): Boolean

}