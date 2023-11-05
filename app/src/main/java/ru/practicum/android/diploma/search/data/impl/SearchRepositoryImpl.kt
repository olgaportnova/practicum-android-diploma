package ru.practicum.android.diploma.search.data.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.filter.domain.models.ParamsFilterModel
import ru.practicum.android.diploma.hhApi.NetworkClient
import ru.practicum.android.diploma.search.domain.SearchRepositry
import ru.practicum.android.diploma.search.domain.models.AnswerVacancyList
import ru.practicum.android.diploma.search.domain.models.QuerySearchMdl
import ru.practicum.android.diploma.sharedPref.FiltersStorage

class SearchRepositoryImpl(
    private val networkClient: NetworkClient,
    private val filtersStorage: FiltersStorage
) : SearchRepositry {
    override suspend fun doRequestSearch(searchRequest: QuerySearchMdl): Flow<AnswerVacancyList> {
        TODO("Not yet implemented")
    }

    override fun getParamsFilters(): ParamsFilterModel? {
        TODO("Not yet implemented")
    }

    fun
    private fun createQueryMap(searchRequest: QuerySearchMdl){}
}