package ru.practicum.android.diploma.favorite.data.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.db.AppDatabase
import ru.practicum.android.diploma.db.entity.FavoriteVacancyEntity
import ru.practicum.android.diploma.favorite.domain.FavoriteRepository
import ru.practicum.android.diploma.search.domain.models.Vacancy
import ru.practicum.android.diploma.util.mappers.VacancyEntityMapper


class FavoriteRepositoryImpl(
    private val appDatabase: AppDatabase,
    private val vacancyConvertor: VacancyEntityMapper
) : FavoriteRepository {

    override suspend fun insertVacancyToFavoriteList(vacancy: Vacancy) {
        appDatabase.favoriteVacancyDao()
            .insertVacancyToFavoriteList(vacancyConvertor.vacancyVacancyToEntity(vacancy))
    }

    override suspend fun deleteVacancyFromFavoriteList(vacancy: Vacancy) {
        appDatabase.favoriteVacancyDao()
            .deleteVacancyFromFavoriteList(vacancyConvertor.vacancyVacancyToEntity(vacancy))
    }

    override suspend fun getAllFavouriteVacancies(): Flow<List<Vacancy>> = flow {
        val favoriteVacancies = appDatabase.favoriteVacancyDao().getAllFavouriteVacancies()
        emit(convertFromVacancyEntityToVacancy(favoriteVacancies))
    }

    override suspend fun getFavouriteVacancyById(id: Int): Vacancy? {
        val favoriteVacancyEntity = appDatabase.favoriteVacancyDao().getFavouriteVacancyById(id)
        return if (favoriteVacancyEntity != null) {
            vacancyConvertor.vacancyEntityToVacancy(favoriteVacancyEntity)
        } else {
            null
        }
    }

    override suspend fun doesVacancyInFavoriteList(id: Int): Boolean {
        return appDatabase.favoriteVacancyDao().doesVacancyInFavoriteList(id)
    }

    private fun convertFromVacancyEntityToVacancy(listOfEntities: List<FavoriteVacancyEntity>): List<Vacancy> {
        return listOfEntities.map { vacancy -> vacancyConvertor.vacancyEntityToVacancy(vacancy) }
    }

}