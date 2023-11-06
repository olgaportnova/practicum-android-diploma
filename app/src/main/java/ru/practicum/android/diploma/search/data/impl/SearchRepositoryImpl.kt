package ru.practicum.android.diploma.search.data.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.filter.domain.models.ParamsFilterModel
import ru.practicum.android.diploma.hhApi.NetworkClient
import ru.practicum.android.diploma.search.data.dto.models.QuerySearchMdlDto
import ru.practicum.android.diploma.search.domain.SearchRepositry
import ru.practicum.android.diploma.search.domain.models.AnswerVacancyList
import ru.practicum.android.diploma.search.domain.models.QuerySearchMdl
import ru.practicum.android.diploma.sharedPref.FiltersStorage
import ru.practicum.android.diploma.util.mappers.AnswerSearchDtoMapper
import ru.practicum.android.diploma.util.mappers.QuerySearchMapper

class SearchRepositoryImpl(
    private val networkClient: NetworkClient,
    private val filtersStorage: FiltersStorage
) : SearchRepositry {
    override suspend fun doRequestSearch(searchRequest: QuerySearchMdl): Flow<AnswerVacancyList> {

        val mapped = QuerySearchMapper.qrSearchMdlToQrSearchMdlDto(searchRequest)
        val response = networkClient.getVacancies(createQueryMap(mapped)).data

        return flow {

            emit(AnswerSearchDtoMapper.answSearchDtoInSearch(response!!))

        }
}


    override fun getParamsFilters(): ParamsFilterModel? {
        TODO("Not yet implemented")
    }

    private fun createQueryMap(searchRequest: QuerySearchMdlDto): HashMap<String,Any>{
        val options: HashMap<String,Any> = HashMap()

         options["page"] = searchRequest.page
         options["per_page"] = searchRequest.perPage
         options["text"] = searchRequest.text
         if(searchRequest.area != null)
             options["area"] = searchRequest.area
         if(searchRequest.parentArea != null)
             options["parent"] = searchRequest.parentArea
         if(searchRequest.industry != null)
             options["industry"] = searchRequest.industry
         if(searchRequest.currency != null)
             options["currency"] = searchRequest.currency
         if(searchRequest.salary != null)
             options["salary"] = searchRequest.salary
         if(searchRequest.onlyWithSalary)
             options["only_with_salary"] = searchRequest.onlyWithSalary

        return options
    }
}