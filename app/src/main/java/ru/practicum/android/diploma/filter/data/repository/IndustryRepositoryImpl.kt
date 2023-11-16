package ru.practicum.android.diploma.filter.data.repository

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import ru.practicum.android.diploma.filter.data.mappers.CategoryConverter
import ru.practicum.android.diploma.filter.domain.interfaces.IndustryRepository
import ru.practicum.android.diploma.filter.domain.models.CategoryData
import ru.practicum.android.diploma.hhApi.NetworkClient
import ru.practicum.android.diploma.hhApi.dto.RequestWrapper
import ru.practicum.android.diploma.util.DataStatus

class IndustryRepositoryImpl(private val networkClient: NetworkClient) : IndustryRepository {
    override suspend fun getIndustries(): Flow<DataStatus<List<CategoryData>>> {
        return flow {
            emit(DataStatus.Loading())

            val result = networkClient.getIndustries()

            when (result.code) {
                QUERY_DONE -> {
                    result.data?.let {
                        if (it.categories.isEmpty()) emit(DataStatus.EmptyContent())
                        else {
                            val lstData = it.categories.map {dtoModel ->
                                CategoryConverter().convertFromDto(dtoModel)
                            }
                            emit(DataStatus.Content(lstData))
                        }
                    }
                }

                NO_CONNECT -> emit(DataStatus.NoConnecting())
                else -> emit(DataStatus.Error(result.code))
            }
        }
            .catch { emit(DataStatus.Error(errorMessage = it.message.toString())) }
            .flowOn(Dispatchers.IO)
    }

    companion object {
        const val NO_CONNECT = 0
        const val QUERY_DONE = 200
    }
}