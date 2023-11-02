package ru.practicum.android.diploma.filter.network

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.practicum.android.diploma.filter.data.impl.dto.CountryResponse
import ru.practicum.android.diploma.filter.data.impl.dto.ApiCountry

class RetrofitClient {

    suspend fun loadData(): Response<List<ApiCountry>> {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.hh.ru/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiHH = retrofit.create(ApiHH::class.java)

        return apiHH.getAreas()

    }

    suspend fun loadDistricts(parentId:Int): Response<CountryResponse> {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.hh.ru/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiHH = retrofit.create(ApiHH::class.java)

        //return apiHH.getDistricts(parentId)
        return apiHH.getDistrictsB()

    }
}