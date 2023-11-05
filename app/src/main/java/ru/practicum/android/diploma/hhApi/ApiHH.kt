package ru.practicum.android.diploma.hhApi

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.QueryMap
import ru.practicum.android.diploma.BuildConfig
import ru.practicum.android.diploma.filter.data.impl.dto.AreaDto
import ru.practicum.android.diploma.filter.data.impl.dto.CountryDto
import ru.practicum.android.diploma.search.data.dto.models.AnswerVacancyListDto
import ru.practicum.android.diploma.search.data.dto.models.VacancyDto

interface ApiHH {

    @GET("areas/countries")
    suspend fun getAreas(): Response<List<CountryDto>>

    @GET("areas/{id}")
    suspend fun getDistricts(@Path("id") id:Int): Response<AreaDto>


    /**
     * Function for get list vacancy with additional parameters in Map
     * @param Map where String - key, Any - type for String and Int value.
     * @return [Response<T>]
     */
    @Headers(
        "Authorization: Bearer ${BuildConfig.HH_ACCESS_TOKEN}",
        "HH-User-Agent: JobPulse/1.0(yep4yep@gmail.com)"
    )
    @GET("vacancies")
    suspend fun getVacancies(@QueryMap options: Map<String, Any>): Response<AnswerVacancyListDto>


    /**
     * Function for get similar vacancy
     * @param String
     * @return [Response]
     */
    @Headers(
        "Authorization: Bearer ${BuildConfig.HH_ACCESS_TOKEN}",
        "HH-User-Agent: JobPulse/1.0(yep4yep@gmail.com)"
    )
    @GET("vacancies/{vacancy_id}/similar_vacancies")
    suspend fun getSimilarVacancy(@Path("vacancy_id") id:String):Response<AnswerVacancyListDto>


    /**
     * Function for get details vacancy
     * @param id vacancy
     * @return [Response]
     */
    @Headers(
        "Authorization: Bearer ${BuildConfig.HH_ACCESS_TOKEN}",
        "HH-User-Agent: JobPulse/1.0(yep4yep@gmail.com)"
    )
    @GET("vacancies/{vacancy_id}")
    suspend fun getVacancyDetails(@Path("vacancy_id")id:String):Response<VacancyDto>
}