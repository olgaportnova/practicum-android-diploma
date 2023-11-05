package ru.practicum.android.diploma.hhApi.impl

import android.content.Context
import retrofit2.Response
import ru.practicum.android.diploma.filter.data.dto.AreaDto
import ru.practicum.android.diploma.filter.data.dto.Category
import ru.practicum.android.diploma.filter.data.dto.CountryDto
import ru.practicum.android.diploma.hhApi.ApiHH
import ru.practicum.android.diploma.hhApi.NetworkClient

class
RetrofitClient(
    private val hhApi: ApiHH,
    private val context: Context
) : NetworkClient {
    override suspend fun loadCountries(): Response<List<CountryDto>> =
        hhApi.getAreas()

    override suspend fun loadDistricts(parentId: Int): Response<AreaDto> =
        hhApi.getDistricts(parentId)

    override suspend fun loadIndustries(): Response<Category> {
        return hhApi.getIndustries()
    }

}