package ru.practicum.android.diploma.favorite.domain

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.search.domain.models.Vacancy
import ru.practicum.android.diploma.search.domain.models.VacancyForTests

interface FavoriteRepository {

    suspend fun insertVacancyToFavoriteList(vacancy: VacancyForTests)

    suspend fun deleteVacancyFromFavoriteList(vacancy: VacancyForTests)

    fun getAllFavouriteVacancies(): Flow<List<VacancyForTests>>

    suspend fun getFavouriteVacancyById(id:Int): VacancyForTests?

    suspend fun doesVacancyInFavoriteList(id: Int): Boolean

}