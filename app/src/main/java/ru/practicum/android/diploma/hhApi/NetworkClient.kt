package ru.practicum.android.diploma.hhApi

import retrofit2.Response
import ru.practicum.android.diploma.filter.data.impl.dto.ApiArea
import ru.practicum.android.diploma.filter.data.impl.dto.ApiCountry

interface NetworkClient {
    //TODO:добавить функцию, либо функции которые будут принимать Any в качестве параметра и возвращать Response обертку

    suspend fun loadCountries(): Response<List<ApiCountry>>
    suspend fun loadDistricts(parentId: Int): Response<ApiArea>

}