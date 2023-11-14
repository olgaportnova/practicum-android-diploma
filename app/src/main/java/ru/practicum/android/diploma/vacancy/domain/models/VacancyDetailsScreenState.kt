package ru.practicum.android.diploma.vacancy.domain.models

import ru.practicum.android.diploma.search.domain.models.Vacancy

sealed interface VacancyDetailsScreenState {
    object Loading : VacancyDetailsScreenState

    object Error : VacancyDetailsScreenState

    data class Content(
        val foundVacancy: Vacancy?
    ) : VacancyDetailsScreenState

}