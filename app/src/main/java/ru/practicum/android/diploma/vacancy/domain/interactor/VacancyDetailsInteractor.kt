package ru.practicum.android.diploma.vacancy.domain.interactor

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.vacancy.domain.models.SearchResult
import ru.practicum.android.diploma.vacancy.domain.models.VacancyDetails

interface VacancyDetailsInteractor {
    suspend fun getVacancyDetails(id: Int): Flow<SearchResult<List<VacancyDetails>?, String?>>
}