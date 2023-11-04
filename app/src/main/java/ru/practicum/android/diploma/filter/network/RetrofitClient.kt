package ru.practicum.android.diploma.filter.network

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.practicum.android.diploma.filter.data.impl.dto.ApiArea
import ru.practicum.android.diploma.filter.data.impl.dto.ApiCountry
import ru.practicum.android.diploma.filter.data.impl.dto.VacancyResponse

class RetrofitClient {
    suspend fun loadData(): Response<List<ApiCountry>> {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.hh.ru/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiHH = retrofit.create(ApiHH::class.java)

        return apiHH.getAreas()

    }

    suspend fun loadDistricts(parentId: Int): Response<ApiArea> {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.hh.ru/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiHH = retrofit.create(ApiHH::class.java)

        return apiHH.getDistricts(parentId)
    }


    suspend fun loadVacanciesByArea():Response<VacancyResponse>{
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.hh.ru/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiHH = retrofit.create(ApiHH::class.java)

        return apiHH.getVacancyByArea(50,113)
    }
}