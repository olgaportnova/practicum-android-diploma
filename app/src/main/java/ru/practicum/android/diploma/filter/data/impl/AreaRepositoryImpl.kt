package ru.practicum.android.diploma.filter.data.impl

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import ru.practicum.android.diploma.filter.domain.interfaces.AreaRepository
import ru.practicum.android.diploma.filter.domain.models.Country
import ru.practicum.android.diploma.filter.network.DataStatus
import ru.practicum.android.diploma.filter.network.RetrofitClient

class AreaRepositoryImpl(private val retrofitClient: RetrofitClient) : AreaRepository {
    override suspend fun loadCountries(): Flow<DataStatus<List<Country>>> {
        return flow {
            emit(DataStatus.Loading())

            val result = retrofitClient.loadData()

            when (result.code()) {
                200 -> {
                    result.body()?.let {
                        val lst = it.map { el -> DataMapper().countryFromPojo(el) }
                        emit(DataStatus.Content(lst))
                    }
                }

                else -> emit(DataStatus.Error(result.code()))
            }
        }.catch {
            emit(DataStatus.ErrorMessage(it.message.toString()))
        }.flowOn(Dispatchers.IO)
    }
}