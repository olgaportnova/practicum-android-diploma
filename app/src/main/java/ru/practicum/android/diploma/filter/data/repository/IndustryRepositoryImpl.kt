package ru.practicum.android.diploma.filter.data.repository

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.filter.data.dto.Category
import ru.practicum.android.diploma.filter.domain.interfaces.IndustryRepository
import ru.practicum.android.diploma.hhApi.impl.RetrofitClient
import ru.practicum.android.diploma.util.DataStatus

class IndustryRepositoryImpl(private val networkClient: RetrofitClient) : IndustryRepository {
    override suspend fun loadIndustries(): Flow<DataStatus<List<Category>>> {
        return flow {
            emit(DataStatus.Loading())

            val industrySearch = networkClient.loadIndustries()
            Log.e("LOG",industrySearch.code().toString())
            when(industrySearch.code()){
                200 -> {
                    val body = industrySearch.body()
                }
            }
        }
    }
}