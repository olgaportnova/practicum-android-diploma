package ru.practicum.android.diploma.hhApi.impl

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import ru.practicum.android.diploma.filter.data.dto.AreaDto
import ru.practicum.android.diploma.filter.data.dto.Category
import ru.practicum.android.diploma.filter.data.dto.CountryDto
import ru.practicum.android.diploma.hhApi.ApiHH
import ru.practicum.android.diploma.hhApi.NetworkClient
import ru.practicum.android.diploma.hhApi.dto.ResponseWrapper
import ru.practicum.android.diploma.search.data.dto.models.AnswerVacancyListDto
import ru.practicum.android.diploma.search.data.dto.models.VacancyDto
import ru.practicum.android.diploma.util.DataStatus

class NetworkClientImpl(
    private val hhApi: ApiHH,
    private val context: Context
) : NetworkClient {

    companion object {
        const val NO_CONNECT = 0
    }

    override suspend fun getAreas(): ResponseWrapper<List<CountryDto>> {
        return withContext(Dispatchers.IO) {

            if (isConnected() == false) {
                ResponseWrapper(NO_CONNECT, data = null)
            } else {
                val response = hhApi.getCountries()
                if (response.code() == 200)
                    ResponseWrapper(response.code(), response.body())
                else
                    ResponseWrapper(response.code(), data = null)
            }
        }
    }

    override suspend fun getDistricts(id: Int): ResponseWrapper<AreaDto> {
        return withContext(Dispatchers.IO) {

            if (isConnected() == false) {
                ResponseWrapper(NO_CONNECT, data = null)
            } else {
                val response = hhApi.getDistricts(id)
                if (response.code() == 200)
                    ResponseWrapper(response.code(), response.body())
                else
                    ResponseWrapper(response.code(), data = null)
            }
        }
    }

    override suspend fun getSimilarVacancy(id: String): ResponseWrapper<AnswerVacancyListDto> {
        return withContext(Dispatchers.IO) {

            if (isConnected() == false) {
                ResponseWrapper(NO_CONNECT, data = null)
            } else {
                val response = hhApi.getSimilarVacancy(id)
                if (response.code() == 200)
                    ResponseWrapper(response.code(), response.body())
                else
                    ResponseWrapper(response.code(), data = null)
            }
        }
    }

    override suspend fun getVacancies(options: Map<String, Any>): ResponseWrapper<AnswerVacancyListDto> {
        return withContext(Dispatchers.IO) {

            if (isConnected() == false) {
                ResponseWrapper(NO_CONNECT, data = null)
            } else {
                val response = hhApi.getVacancies(options)
                if (response.code() == 200)
                    ResponseWrapper(response.code(), response.body())
                else
                    ResponseWrapper(response.code(), data = null)
            }
        }
    }

    override suspend fun getVacancyDetails(id: String): ResponseWrapper<VacancyDto> {
        return withContext(Dispatchers.IO) {

            if (isConnected() == false) {
                ResponseWrapper(NO_CONNECT, data = null)
            } else {
                val response = hhApi.getVacancyDetails(id)
                if (response.code() == 200)
                    ResponseWrapper(response.code(), response.body())
                else
                    ResponseWrapper(response.code(), data = null)
            }
        }
    }

    override suspend fun loadIndustries(): Flow<DataStatus<List<Category>>> {
        return flow {  }
    }


    @SuppressLint("ServiceCast")
    private fun isConnected(): Boolean {
        val connectivityManager = context.getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> return true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> return true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> return true
            }
        }
        return false
    }
}