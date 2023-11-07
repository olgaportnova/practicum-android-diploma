package ru.practicum.android.diploma.favorite.domain

import ru.practicum.android.diploma.search.domain.models.Vacancy
import ru.practicum.android.diploma.search.domain.models.VacancyForTests


sealed class FavoriteState {

    data class Success(
        val vacancies: List<VacancyForTests>
    ) : FavoriteState()

    object EmptyList : FavoriteState()
    object Error : FavoriteState()
}
