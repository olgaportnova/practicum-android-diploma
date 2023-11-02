package ru.practicum.android.diploma.filter.data.impl

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import ru.practicum.android.diploma.filter.domain.interfaces.AreaRepository
import ru.practicum.android.diploma.filter.domain.models.Area
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
        }
            .catch { emit(DataStatus.ErrorMessage(it.message.toString())) }
            .flowOn(Dispatchers.IO)
    }

    override suspend fun loadDistricts(parentId: Int): Flow<DataStatus<Area>> {
        return flow {
            // Отправка информации о старте загрузки
            emit(DataStatus.Loading())

            val result = retrofitClient.loadDistricts(parentId)

            when(result.code()){
                200 ->{
                    //Log.e("LOG",result.body().toString())
                    emit(DataStatus.Content(DataMapper().convert(result.body()!!)))
                }
            }
        }
            .catch { Log.e("LOG", it.message.toString()) }
            .flowOn(Dispatchers.IO)
    }
}