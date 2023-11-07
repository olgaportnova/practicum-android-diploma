package ru.practicum.android.diploma.favorite.data.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.db.AppDatabase
import ru.practicum.android.diploma.db.entity.FavoriteVacancyEntity
import ru.practicum.android.diploma.favorite.domain.FavoriteRepository
import ru.practicum.android.diploma.search.domain.models.Vacancy
import ru.practicum.android.diploma.search.domain.models.VacancyForTests
import ru.practicum.android.diploma.util.mappers.VacancyEntityMapper


class FavoriteRepositoryImpl(
    private val appDatabase: AppDatabase,
    private val vacancyConvertor: VacancyEntityMapper
) : FavoriteRepository {

    override suspend fun insertVacancyToFavoriteList(vacancy: VacancyForTests) {
        appDatabase.favoriteVacancyDao()
            .insertVacancyToFavoriteList(vacancyConvertor.vacancyVacancyToEntity(vacancy))
    }

    override suspend fun deleteVacancyFromFavoriteList(vacancy: VacancyForTests) {
        appDatabase.favoriteVacancyDao()
            .deleteVacancyFromFavoriteList(vacancyConvertor.vacancyVacancyToEntity(vacancy))
    }

    override fun getAllFavouriteVacancies(): Flow<List<VacancyForTests>> = flow {
        val favoriteVacancies = appDatabase.favoriteVacancyDao().getAllFavouriteVacancies()
        emit(convertFromVacancyEntityToVacancy(favoriteVacancies))
    }

    override suspend fun getFavouriteVacancyById(id: Int): VacancyForTests? {
        val favoriteVacancyEntity = appDatabase.favoriteVacancyDao().getFavouriteVacancyById(id)
        return if (favoriteVacancyEntity != null) {
            vacancyConvertor.vacancyEntityToVacancy(favoriteVacancyEntity)
        } else {
            //TODO обсудить с Таней, что возвращать на экран деталей если ошибка получения информации из БД
            null
        }
    }

    override suspend fun doesVacancyInFavoriteList(id: Int): Boolean {
        return appDatabase.favoriteVacancyDao().doesVacancyInFavoriteList(id)
    }

    private fun convertFromVacancyEntityToVacancy(listOfEntities: List<FavoriteVacancyEntity>): List<VacancyForTests> {
        return listOfEntities.map { vacancy -> vacancyConvertor.vacancyEntityToVacancy(vacancy) }
    }

}