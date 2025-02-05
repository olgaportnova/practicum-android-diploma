package ru.practicum.android.diploma.hhApi

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.QueryMap
import ru.practicum.android.diploma.BuildConfig
import ru.practicum.android.diploma.filter.data.dto.models.AreaDto
import ru.practicum.android.diploma.filter.data.dto.models.CountryDto
import ru.practicum.android.diploma.filter.data.dto.models.CategoryResponse
import ru.practicum.android.diploma.search.data.dto.models.AnswerVacancyListDto
import ru.practicum.android.diploma.search.data.dto.models.VacancyDto

interface ApiHH {

    @Headers(
        "Authorization: Bearer ${BuildConfig.HH_ACCESS_TOKEN}",
        "HH-User-Agent: JobPulse/1.0(yep4yep@gmail.com)"
    )
    @GET("areas/countries")
    suspend fun getCountries(): Response<List<CountryDto>>

    @Headers(
        "Authorization: Bearer ${BuildConfig.HH_ACCESS_TOKEN}",
        "HH-User-Agent: JobPulse/1.0(yep4yep@gmail.com)"
    )
    @GET("areas/{id}")
    suspend fun getDistricts(@Path("id") id: Int): Response<AreaDto>

    @Headers(
        "Authorization: Bearer ${BuildConfig.HH_ACCESS_TOKEN}",
        "HH-User-Agent: JobPulse/1.0(yep4yep@gmail.com)"
    )
    @GET("areas")
    suspend fun getAreaTree(): Response<List<AreaDto>>

    @Headers(
        "Authorization: Bearer ${BuildConfig.HH_ACCESS_TOKEN}",
        "HH-User-Agent: JobPulse/1.0(yep4yep@gmail.com)"
    )
    @GET("professional_roles")
    suspend fun getIndustries(): Response<CategoryResponse>

    @Headers(
        "Authorization: Bearer ${BuildConfig.HH_ACCESS_TOKEN}",
        "HH-User-Agent: JobPulse/1.0(yep4yep@gmail.com)"
    )
    @GET("vacancies")
    suspend fun getVacancies(@QueryMap options: HashMap<String, Any>): Response<AnswerVacancyListDto>

    @Headers(
        "Authorization: Bearer ${BuildConfig.HH_ACCESS_TOKEN}",
        "HH-User-Agent: JobPulse/1.0(yep4yep@gmail.com)"
    )
    @GET("vacancies/{vacancy_id}/similar_vacancies")
    suspend fun getSimilarVacancy(@Path("vacancy_id") id: String): Response<AnswerVacancyListDto>

    @Headers(
        "Authorization: Bearer ${BuildConfig.HH_ACCESS_TOKEN}",
        "HH-User-Agent: JobPulse/1.0(yep4yep@gmail.com)"
    )
    @GET("vacancies/{vacancy_id}")
    suspend fun getVacancyDetails(@Path("vacancy_id") id: String): Response<VacancyDto>
}