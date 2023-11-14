package ru.practicum.android.diploma.hhApi.impl

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.practicum.android.diploma.filter.data.dto.models.AreaDto
import ru.practicum.android.diploma.filter.data.dto.models.CountryDto
import ru.practicum.android.diploma.filter.data.dto.models.CategoryResponse
import ru.practicum.android.diploma.hhApi.ApiHH
import ru.practicum.android.diploma.hhApi.NetworkClient
import ru.practicum.android.diploma.hhApi.dto.RequestWrapper
import ru.practicum.android.diploma.hhApi.dto.ResponseWrapper
import ru.practicum.android.diploma.search.data.dto.models.AnswerVacancyListDto
import ru.practicum.android.diploma.search.data.dto.models.VacancyDto

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
    //id: Int
    override suspend fun getDistricts(id:RequestWrapper<Int>): ResponseWrapper<AreaDto> {
        return withContext(Dispatchers.IO) {

            if (isConnected() == false) {
                ResponseWrapper(NO_CONNECT, data = null)
            } else {
                val response = hhApi.getDistricts(id.query)
                if (response.code() == 200)
                    ResponseWrapper(response.code(), response.body())
                else
                    ResponseWrapper(response.code(), data = null)
            }
        }
    }

    override suspend fun getSimilarVacancy(id:RequestWrapper<String>): ResponseWrapper<AnswerVacancyListDto> {
        return withContext(Dispatchers.IO) {

            if (isConnected() == false) {
                ResponseWrapper(NO_CONNECT, data = null)
            } else {
                val response = hhApi.getSimilarVacancy(id.query)
                if (response.code() == 200)
                    ResponseWrapper(response.code(), response.body())
                else
                    ResponseWrapper(response.code(), data = null)
            }
        }
    }

    override suspend fun getVacancies(options: RequestWrapper<HashMap<String, Any>>): ResponseWrapper<AnswerVacancyListDto> {
        return withContext(Dispatchers.IO) {

            if (isConnected() == false) {
                ResponseWrapper(NO_CONNECT, data = null)
            } else {
                val response = hhApi.getVacancies(options.query)
                if (response.code() == 200)
                    ResponseWrapper(response.code(), response.body())
                else
                    ResponseWrapper(response.code(), data = null)
            }
        }
    }

    override suspend fun getVacancyDetails(id:RequestWrapper<String>): ResponseWrapper<VacancyDto> {
        return withContext(Dispatchers.IO) {

            if (isConnected() == false) {
                ResponseWrapper(code = NO_CONNECT, data = null)
            } else {
                val response = hhApi.getVacancyDetails(id.query)
                if (response.code() == 200)
                    ResponseWrapper(code = response.code(), data = response.body())
                else
                    ResponseWrapper(code = response.code(), data = response.body())
            }
        }
    }

    override suspend fun getIndustries(): ResponseWrapper<CategoryResponse> {
        val response = hhApi.getIndustries()

        return if (response.code() == 200) ResponseWrapper(response.code(), response.body())
        else ResponseWrapper(response.code(), data = null)
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