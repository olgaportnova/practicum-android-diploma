package ru.practicum.android.diploma.hhApi

import ru.practicum.android.diploma.filter.data.dto.models.AreaDto
import ru.practicum.android.diploma.filter.data.dto.models.CountryDto
import ru.practicum.android.diploma.filter.data.dto.models.CategoryResponse
import ru.practicum.android.diploma.hhApi.dto.ResponseWrapper
import ru.practicum.android.diploma.search.data.dto.models.AnswerVacancyListDto
import ru.practicum.android.diploma.search.data.dto.models.VacancyDto

interface NetworkClient {
    suspend fun getAreas(): ResponseWrapper<List<CountryDto>>

    suspend fun getDistricts(id: Int): ResponseWrapper<AreaDto>

    suspend fun getVacancies(options: Map<String, Any>): ResponseWrapper<AnswerVacancyListDto>

    suspend fun getSimilarVacancy(id: String): ResponseWrapper<AnswerVacancyListDto>

    suspend fun getVacancyDetails(dto: Any): ResponseWrapper<VacancyDto>

    suspend fun getIndustries(): ResponseWrapper<CategoryResponse>
}