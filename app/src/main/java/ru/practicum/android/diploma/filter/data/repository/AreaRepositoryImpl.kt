package ru.practicum.android.diploma.filter.data.repository

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import ru.practicum.android.diploma.filter.data.mappers.CountryConverter
import ru.practicum.android.diploma.filter.data.mappers.DistrictConverter
import ru.practicum.android.diploma.filter.domain.interfaces.AreaRepository
import ru.practicum.android.diploma.filter.domain.models.AreaData
import ru.practicum.android.diploma.hhApi.NetworkClient
import ru.practicum.android.diploma.hhApi.dto.RequestWrapper
import ru.practicum.android.diploma.util.DataStatus

class AreaRepositoryImpl(private val networkClient: NetworkClient) : AreaRepository {
    override suspend fun loadCountries(): Flow<DataStatus<List<AreaData>>> {
        return flow {
            emit(DataStatus.Loading())

            val result = networkClient.getAreas()

            when (result.code) {
                200 -> {
                    result.data?.let {
                        if (it.isEmpty()) emit(DataStatus.EmptyContent())
                        else{
                            val lst = it.map { el -> CountryConverter().convertFromDto(el) }
                            emit(DataStatus.Content(lst))
                        }
                    }
                }
                0->emit(DataStatus.NoConnecting())
                else -> emit(DataStatus.Error(result.code))
            }
        }
            .catch { emit(DataStatus.Error(errorMessage = it.message.toString())) }
            .flowOn(Dispatchers.IO)
    }

    override suspend fun loadDistricts(parentId: Int): Flow<DataStatus<AreaData>> {
        return flow {
            emit(DataStatus.Loading()) // Отправка информации о старте загрузки

            val result = networkClient.getDistricts(RequestWrapper(parentId))

            when (result.code) {
                200 -> {
                    emit(DataStatus.Content(DistrictConverter().convertFromDto(result.data!!)))
                }
            }
        }
            .catch { Log.e("LOG", it.message.toString()) }
            .flowOn(Dispatchers.IO)
    }
}