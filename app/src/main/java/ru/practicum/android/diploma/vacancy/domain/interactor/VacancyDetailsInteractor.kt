package ru.practicum.android.diploma.vacancy.domain.interactor

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.search.domain.models.Vacancy
import ru.practicum.android.diploma.vacancy.domain.models.SearchResult

interface VacancyDetailsInteractor {
    suspend fun getVacancyDetails(id: Int): Flow<SearchResult<Vacancy?, String?>>
}