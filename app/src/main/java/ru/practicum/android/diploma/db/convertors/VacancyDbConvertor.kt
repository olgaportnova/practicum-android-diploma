package ru.practicum.android.diploma.db.convertors

import ru.practicum.android.diploma.db.entity.FavoriteVacancyEntity
import ru.practicum.android.diploma.favorite.domain.models.Vacancy

class VacancyDbConvertor {

    fun map(vacancy: Vacancy) : FavoriteVacancyEntity {
        return FavoriteVacancyEntity (
            id =vacancy.id,
            name = vacancy.name
           //TODO реализовать конвертер
        )
    }
    fun map(vacancyEntity: FavoriteVacancyEntity): Vacancy {
        return Vacancy(
            id = vacancyEntity.id,
            name = vacancyEntity.name
            //TODO реализовать конвертер
        )
    }

}