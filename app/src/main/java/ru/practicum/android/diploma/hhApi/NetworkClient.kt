package ru.practicum.android.diploma.hhApi

import ru.practicum.android.diploma.filter.data.dto.models.AreaDto
import ru.practicum.android.diploma.filter.data.dto.models.CountryDto
import ru.practicum.android.diploma.filter.data.dto.models.CategoryResponse
import ru.practicum.android.diploma.hhApi.dto.RequestWrapper
import ru.practicum.android.diploma.hhApi.dto.ResponseWrapper
import ru.practicum.android.diploma.search.data.dto.models.AnswerVacancyListDto
import ru.practicum.android.diploma.search.data.dto.models.VacancyDto

interface NetworkClient {
    suspend fun getAreas(): ResponseWrapper<List<CountryDto>>

    suspend fun getAreaTree():ResponseWrapper<List<AreaDto>>

    suspend fun getDistricts(request: RequestWrapper<Int>): ResponseWrapper<AreaDto>

    suspend fun getVacancies(request: RequestWrapper<HashMap<String, Any>>): ResponseWrapper<AnswerVacancyListDto>

    suspend fun getSimilarVacancy(request: RequestWrapper<String>): ResponseWrapper<AnswerVacancyListDto>

    suspend fun getVacancyDetails(request: RequestWrapper<String>): ResponseWrapper<VacancyDto>

    suspend fun getIndustries(): ResponseWrapper<CategoryResponse>
}