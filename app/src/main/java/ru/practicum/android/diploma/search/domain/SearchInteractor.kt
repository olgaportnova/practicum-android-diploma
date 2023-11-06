package ru.practicum.android.diploma.search.domain

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.search.domain.models.AnswerVacancyList
import ru.practicum.android.diploma.search.domain.models.QuerySearchMdl

interface SearchInteractor {
    suspend fun getTestRequest(request: QuerySearchMdl) : Flow<AnswerVacancyList>
}