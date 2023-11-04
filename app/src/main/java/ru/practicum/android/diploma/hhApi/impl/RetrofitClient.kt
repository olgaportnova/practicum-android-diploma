package ru.practicum.android.diploma.hhApi.impl

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.practicum.android.diploma.filter.data.impl.dto.ApiArea
import ru.practicum.android.diploma.filter.data.impl.dto.ApiCountry
import ru.practicum.android.diploma.hhApi.ApiHH

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

    //TODO данный класс должен стать NetworkClientImpl и содержать функцию по обработке ошибок, примерно как вот тут - AreaRepositoryImpl
    //TODO Ретрофит должен приходить в конструктор из DI
}