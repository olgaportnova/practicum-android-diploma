package ru.practicum.android.diploma.hhApi

import ru.practicum.android.diploma.filter.data.impl.dto.ApiArea
import ru.practicum.android.diploma.filter.data.impl.dto.ApiCountry
import ru.practicum.android.diploma.hhApi.dto.ResponseWrapper
import ru.practicum.android.diploma.search.data.dto.models.AnswerVacancyListDto
import ru.practicum.android.diploma.search.data.dto.models.VacancyDto

interface NetworkClient {
    suspend fun getAreas(): ResponseWrapper<List<ApiCountry>>

    suspend fun getDistricts(id:Int): ResponseWrapper<ApiArea>

    suspend fun getVacancies(options: Map<String, Any>): ResponseWrapper<AnswerVacancyListDto>

    suspend fun getSimilarVacancy(id:String): ResponseWrapper<AnswerVacancyListDto>

    suspend fun getVacancyDetails(id:String):ResponseWrapper<VacancyDto>
}