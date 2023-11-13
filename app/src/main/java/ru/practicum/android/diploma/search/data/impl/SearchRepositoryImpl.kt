package ru.practicum.android.diploma.search.data.impl

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import ru.practicum.android.diploma.filter.data.mappers.FilterConverter
import ru.practicum.android.diploma.filter.domain.models.FilterData
import ru.practicum.android.diploma.hhApi.NetworkClient
import ru.practicum.android.diploma.hhApi.dto.RequestWrapper
import ru.practicum.android.diploma.search.data.dto.models.QuerySearchMdlDto
import ru.practicum.android.diploma.search.domain.SearchRepository
import ru.practicum.android.diploma.search.domain.models.AnswerVacancyList
import ru.practicum.android.diploma.search.domain.models.QuerySearchMdl
import ru.practicum.android.diploma.sharedPref.FiltersStorage
import ru.practicum.android.diploma.util.DataStatus
import ru.practicum.android.diploma.util.mappers.AnswerSearchDtoMapper
import ru.practicum.android.diploma.util.mappers.QuerySearchMapper

class SearchRepositoryImpl(
    private val networkClient: NetworkClient,
    private val filtersStorage: FiltersStorage
) : SearchRepository {

    companion object {
        const val NO_CONNECT = 0
        const val QUERY_DONE = 200
    }

    override suspend fun doRequestSearch(searchRequest: QuerySearchMdl): Flow<DataStatus<AnswerVacancyList>> {

        return flow {

            emit(DataStatus.Loading())

            val mapped = QuerySearchMapper.qrSearchMdlToQrSearchMdlDto(searchRequest)
            val response = networkClient.getVacancies(RequestWrapper(createQueryMap(mapped)))

            when (response.code) {
               NO_CONNECT -> {
                   emit(DataStatus.NoConnecting())
               }
               QUERY_DONE -> {
                   if(response.data == null || response.data.found == 0 || response.data.listVacancy.isEmpty()){
                       emit(DataStatus.EmptyContent())
                   }
                   else{
                       emit(DataStatus.Content(AnswerSearchDtoMapper.answSearchDtoInSearch(response.data)))
                   }
               }
                else->{
                    emit(DataStatus.Error(response.code))
                }
            }

        }.catch { emit(DataStatus.Error()) }
         .flowOn(Dispatchers.IO)
    }


    override fun getParamsFilters(): FilterData? {
        val params = filtersStorage.getParamsFilters()

        if(params != null)
          return FilterConverter().convertFromDto(params)
        else return null
    }

    private fun createQueryMap(searchRequest: QuerySearchMdlDto): HashMap<String, Any> {
        val options: HashMap<String, Any> = HashMap()

        options["page"] = searchRequest.page
        options["per_page"] = searchRequest.perPage
        options["text"] = searchRequest.text
        if (searchRequest.area != null)
            options["area"] = searchRequest.area
        if (searchRequest.parentArea != null)
            options["parent"] = searchRequest.parentArea
        if (searchRequest.industry != null)
            options["industry"] = searchRequest.industry
        if (searchRequest.currency != null)
            options["currency"] = searchRequest.currency
        if (searchRequest.salary != null)
            options["salary"] = searchRequest.salary
        if (searchRequest.onlyWithSalary)
            options["only_with_salary"] = searchRequest.onlyWithSalary

        return options
    }
}