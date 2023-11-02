package ru.practicum.android.diploma.filter.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import ru.practicum.android.diploma.filter.data.impl.dto.ApiArea
import ru.practicum.android.diploma.filter.data.impl.dto.ApiCountry

interface ApiHH {
    @GET("areas/countries")
    suspend fun getAreas(): Response<List<ApiCountry>>


    @GET("areas/{id}")
    suspend fun getDistricts(@Path("id") id:Int): Response<ApiArea>

    @GET("areas/113")
    suspend fun getDistrictsB(): Response<ApiArea>
}