package ru.practicum.android.diploma.vacancy.data.repository

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.search.domain.models.Vacancy
import ru.practicum.android.diploma.util.DataResource

interface VacancyDetailsRepository {
    suspend fun getVacancyDetails(id: Int): Flow<DataResource<Vacancy>>
}