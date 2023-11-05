package ru.practicum.android.diploma.search.domain

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.filter.domain.models.ParamsFilterModel
import ru.practicum.android.diploma.search.domain.models.AnswerVacancyList
import ru.practicum.android.diploma.search.domain.models.QuerySearchMdl

interface SearchRepositry {

    suspend fun doRequestSearch(searchRequest:QuerySearchMdl): Flow<AnswerVacancyList>

    fun getParamsFilters(): ParamsFilterModel?
}