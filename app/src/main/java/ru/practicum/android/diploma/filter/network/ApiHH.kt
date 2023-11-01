package ru.practicum.android.diploma.filter.network

import retrofit2.Response
import retrofit2.http.GET
import ru.practicum.android.diploma.filter.data.impl.pojo.CountryW

interface ApiHH {
    @GET("areas/countries")
    suspend fun getAreas(): Response<List<CountryW>>
}