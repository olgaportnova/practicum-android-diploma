package ru.practicum.android.diploma.filter.network

import retrofit2.http.GET
import ru.practicum.android.diploma.filter.domain.models.Country

interface ApiHH {
    @GET("areas/countries")
    suspend fun getAreas():List<Country>
}