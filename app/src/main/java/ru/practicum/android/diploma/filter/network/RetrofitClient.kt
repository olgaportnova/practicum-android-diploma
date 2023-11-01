package ru.practicum.android.diploma.filter.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.practicum.android.diploma.filter.domain.models.Country
import ru.practicum.android.diploma.util.NetworkResponse

class RetrofitClient {

    suspend fun loadData():NetworkResponse{
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.hh.ru/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiHH = retrofit.create(ApiHH::class.java)

        return try {
            val networkResponse = apiHH.getAreas().execute()
            if (networkResponse.isSuccessful) NetworkResponse().setNewData(networkResponse.body())
            else NetworkResponse().setResponseCode(networkResponse.code())
        } catch (e:Throwable){
            NetworkResponse().transferException(e)
        }
    }
}