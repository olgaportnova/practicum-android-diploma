package ru.practicum.android.diploma.search.domain

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.filter.domain.models.FilterData
import ru.practicum.android.diploma.search.domain.models.AnswerVacancyList
import ru.practicum.android.diploma.search.domain.models.QuerySearchMdl
import ru.practicum.android.diploma.util.DataStatus

interface SearchRepository {

    suspend fun doRequestSearch(searchRequest:QuerySearchMdl): Flow<DataStatus<AnswerVacancyList>>

    fun getParamsFilters(): FilterData?
}