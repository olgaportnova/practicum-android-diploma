package ru.practicum.android.diploma.favorite.domain.models

import ru.practicum.android.diploma.db.entity.FavoriteVacancyEntity

class VacancyConvertor {

    fun mapVacancyToEntity(vacancy: Vacancy): FavoriteVacancyEntity {
        return FavoriteVacancyEntity(
            id = vacancy.id,
            name = vacancy.name
            //TODO реализовать конвертер
        )
    }

    fun mapEntityToVacancy(vacancyEntity: FavoriteVacancyEntity): Vacancy {
        return Vacancy(
            id = vacancyEntity.id,
            name = vacancyEntity.name
            //TODO реализовать конвертер
        )
    }


}