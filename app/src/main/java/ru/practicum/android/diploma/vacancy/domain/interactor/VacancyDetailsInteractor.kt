package ru.practicum.android.diploma.vacancy.domain.interactor

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.search.domain.models.Vacancy
import ru.practicum.android.diploma.util.DataStatus

interface VacancyDetailsInteractor {
    suspend fun getVacancyDetails(id: String): Flow<DataStatus<Vacancy>>
}