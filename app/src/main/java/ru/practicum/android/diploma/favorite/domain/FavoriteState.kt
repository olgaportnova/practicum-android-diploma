package ru.practicum.android.diploma.favorite.domain

import ru.practicum.android.diploma.search.domain.models.Vacancy


sealed class FavoriteState {

    data class Success(
        val vacancies: List<Vacancy>
    ) : FavoriteState()

    object EmptyList : FavoriteState()
    object Error : FavoriteState()
}
