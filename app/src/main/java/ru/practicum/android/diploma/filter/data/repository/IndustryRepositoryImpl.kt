package ru.practicum.android.diploma.filter.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.filter.data.dto.Category
import ru.practicum.android.diploma.filter.domain.interfaces.IndustryRepository
import ru.practicum.android.diploma.hhApi.NetworkClient
import ru.practicum.android.diploma.util.DataStatus

class IndustryRepositoryImpl(private val networkClient: NetworkClient) : IndustryRepository {
    override suspend fun loadIndustries(): Flow<DataStatus<List<Category>>> {
        return flow {
            emit(DataStatus.Loading())

        }
    }
}