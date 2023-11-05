package ru.practicum.android.diploma.hhApi

import retrofit2.Response
import ru.practicum.android.diploma.filter.data.dto.AreaDto
import ru.practicum.android.diploma.filter.data.dto.Category
import ru.practicum.android.diploma.filter.data.dto.CountryDto

interface NetworkClient {
    //TODO:добавить функцию, либо функции которые будут принимать Any в качестве параметра и возвращать Response обертку

    suspend fun loadCountries(): Response<List<CountryDto>>
    suspend fun loadDistricts(parentId: Int): Response<AreaDto>

    suspend fun loadIndustries():Response<Category>

}