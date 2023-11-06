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
import ru.practicum.android.diploma.hhApi.dto.ResponseWrapper
import ru.practicum.android.diploma.search.data.dto.models.AnswerVacancyListDto
import ru.practicum.android.diploma.search.data.dto.models.VacancyDto
import ru.practicum.android.diploma.vacancy.data.dto.VacancyDetailsRequest

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

    override suspend fun getVacancies(options: HashMap<String, Any>): ResponseWrapper<AnswerVacancyListDto> {
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

    override suspend fun getVacancyDetails(dto: Any): ResponseWrapper<VacancyDto> {
        return withContext(Dispatchers.IO) {

            if (isConnected() == false) {
                ResponseWrapper(code = NO_CONNECT, data = null)
            }

            if(dto !is VacancyDetailsRequest){
                ResponseWrapper(code = 400, data = null)
            } else {
                val response = hhApi.getVacancyDetails(dto.id)
                if (response.code == 200)
                    ResponseWrapper(code = response.code, data = response.result)
                else
                    ResponseWrapper(code = response.code, data = null)
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