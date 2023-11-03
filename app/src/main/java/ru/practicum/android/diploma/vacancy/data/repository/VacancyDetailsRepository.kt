package ru.practicum.android.diploma.vacancy.data.repository

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.util.DataResource
import ru.practicum.android.diploma.vacancy.domain.models.VacancyDetails

interface VacancyDetailsRepository {
    suspend fun getVacancyDetails(id: Int): Flow<DataResource<VacancyDetails>>
}