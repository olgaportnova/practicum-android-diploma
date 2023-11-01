package ru.practicum.android.diploma.filter.data.impl

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.practicum.android.diploma.filter.domain.interfaces.AreaRepository
import ru.practicum.android.diploma.filter.network.RetrofitClient
import ru.practicum.android.diploma.util.DataResource

class AreaRepositoryImpl(private val retrofitClient: RetrofitClient) : AreaRepository {
    override suspend fun loadCountries(): DataResource<String> {
        val response = retrofitClient.loadData()
        if(response.networkData!=null){
            val o =55
        }


        return DataResource.Data(data = "Temporal answer")
    }
}