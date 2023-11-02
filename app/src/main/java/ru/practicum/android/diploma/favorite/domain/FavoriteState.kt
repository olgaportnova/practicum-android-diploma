package ru.practicum.android.diploma.favorite.domain

import ru.practicum.android.diploma.favorite.domain.models.Vacancy

sealed class FavoriteState {

    data class Success(
        //TODO: create vacancy model
        val vacancies: List<Vacancy>
    ) : FavoriteState()

    object EmptyList : FavoriteState()
    object Error : FavoriteState()
}
