package ru.practicum.android.diploma.vacancy.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.search.domain.models.Vacancy
import ru.practicum.android.diploma.util.DataStatus

interface VacancyDetailsRepository {
    suspend fun getVacancyDetails(id: String): Flow<DataStatus<Vacancy>>
}