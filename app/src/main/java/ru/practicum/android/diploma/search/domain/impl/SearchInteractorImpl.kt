package ru.practicum.android.diploma.search.domain.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.filter.domain.models.FilterData
import ru.practicum.android.diploma.search.domain.SearchInteractor
import ru.practicum.android.diploma.search.domain.SearchRepository
import ru.practicum.android.diploma.search.domain.models.AnswerVacancyList
import ru.practicum.android.diploma.search.domain.models.QuerySearchMdl
import ru.practicum.android.diploma.util.DataStatus

class SearchInteractorImpl(private val searchRepository: SearchRepository) : SearchInteractor {

    override suspend fun doRequestSearch(searchRequest: QuerySearchMdl): Flow<DataStatus<AnswerVacancyList>> =
        searchRepository.doRequestSearch(searchRequest)

    override fun getParamsFilters(): FilterData? = searchRepository.getParamsFilters()

}