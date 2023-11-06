package ru.practicum.android.diploma.filter.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.filter.data.dto.models.Category
import ru.practicum.android.diploma.filter.domain.interfaces.IndustryRepository
import ru.practicum.android.diploma.hhApi.NetworkClient
import ru.practicum.android.diploma.util.DataStatus

class IndustryRepositoryImpl(private val networkClient: NetworkClient) : IndustryRepository {
    override suspend fun getIndustries(): Flow<DataStatus<List<Category>>> {
        return flow {
            emit(DataStatus.Loading())

            val result = networkClient.getIndustries()
            when (result.code) {
                200 -> {
                    result.data?.let {
                        if (it.categories.isEmpty()) emit(DataStatus.EmptyContent())
                        else {
                            val lst = it.categories
                            emit(DataStatus.Content(lst))
                        }
                    }
                }

                0 -> emit(DataStatus.NoConnecting())
                else -> emit(DataStatus.Error(result.code))
            }
        }
    }
}