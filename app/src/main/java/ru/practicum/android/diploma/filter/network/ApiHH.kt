package ru.practicum.android.diploma.filter.network

import retrofit2.Call
import retrofit2.http.GET
import ru.practicum.android.diploma.filter.domain.models.Country

interface ApiHH {
    @GET("areas/countries8")
    suspend fun getAreas():Call<List<Country>>
}