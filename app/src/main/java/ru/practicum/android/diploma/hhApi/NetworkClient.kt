package ru.practicum.android.diploma.hhApi

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.filter.data.dto.AreaDto
import ru.practicum.android.diploma.filter.data.dto.Category
import ru.practicum.android.diploma.filter.data.dto.CountryDto
import ru.practicum.android.diploma.hhApi.dto.ResponseWrapper
import ru.practicum.android.diploma.search.data.dto.models.AnswerVacancyListDto
import ru.practicum.android.diploma.search.data.dto.models.VacancyDto
import ru.practicum.android.diploma.util.DataStatus

interface NetworkClient {
    suspend fun getAreas(): ResponseWrapper<List<CountryDto>>

    suspend fun getDistricts(id: Int): ResponseWrapper<AreaDto>

    suspend fun getVacancies(options: Map<String, Any>): ResponseWrapper<AnswerVacancyListDto>

    suspend fun getSimilarVacancy(id: String): ResponseWrapper<AnswerVacancyListDto>

    suspend fun getVacancyDetails(id: String): ResponseWrapper<VacancyDto>

    suspend fun loadIndustries(): Flow<DataStatus<List<Category>>>
}