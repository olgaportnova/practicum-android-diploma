package ru.practicum.android.diploma.filter.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query
import ru.practicum.android.diploma.BuildConfig
import ru.practicum.android.diploma.filter.data.impl.dto.ApiArea
import ru.practicum.android.diploma.filter.data.impl.dto.ApiCountry
import ru.practicum.android.diploma.filter.data.impl.dto.VacancyResponse


interface ApiHH {

    @GET("areas/countries")
    suspend fun getAreas(): Response<List<ApiCountry>>


    @GET("areas/{id}")
    suspend fun getDistricts(@Path("id") id: Int): Response<ApiArea>

    @Headers(
        "Authorization: Bearer ${BuildConfig.HH_ACCESS_TOKEN}",
        "HH-User-Agent: JobPulse/1.0(yep4yep@gmail.com)"
    )
    @GET("vacancies")
    suspend fun getVacancyByArea(
        @Query("perPage") perPage:Int,
        @Query("area") area:Int
    ): Response<VacancyResponse>
}