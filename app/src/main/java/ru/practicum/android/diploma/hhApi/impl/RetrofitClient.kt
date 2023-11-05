package ru.practicum.android.diploma.hhApi.impl

import android.content.Context
import retrofit2.Response
import ru.practicum.android.diploma.filter.data.impl.dto.ApiArea
import ru.practicum.android.diploma.filter.data.impl.dto.ApiCountry
import ru.practicum.android.diploma.hhApi.ApiHH
import ru.practicum.android.diploma.hhApi.NetworkClient

class RetrofitClient(
    private val hhApi: ApiHH,
    private val context: Context
) : NetworkClient {
    override suspend fun loadCountries(): Response<List<ApiCountry>> =
        hhApi.getAreas()

    override suspend fun loadDistricts(parentId: Int): Response<ApiArea> =
        hhApi.getDistricts(parentId)

}