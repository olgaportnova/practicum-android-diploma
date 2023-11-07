package ru.practicum.android.diploma.similar.data.impl

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import ru.practicum.android.diploma.filter.data.mappers.CountryConverter
import ru.practicum.android.diploma.filter.domain.models.AreaData
import ru.practicum.android.diploma.hhApi.NetworkClient
import ru.practicum.android.diploma.hhApi.dto.RequestWrapper
import ru.practicum.android.diploma.search.data.dto.models.VacancyDto
import ru.practicum.android.diploma.search.domain.models.AnswerVacancyList
import ru.practicum.android.diploma.search.domain.models.Vacancy
import ru.practicum.android.diploma.similar.domain.SimilarRepository
import ru.practicum.android.diploma.util.DataStatus
import ru.practicum.android.diploma.util.mappers.AnswerSearchDtoMapper
import ru.practicum.android.diploma.util.mappers.VacancyDtoMapper

class SimilarRepositoryImpl(
    private val networkClient: NetworkClient,
    private val converter: VacancyDtoMapper
): SimilarRepository {

    override suspend fun getSimilarVacancies(id: String): Flow<DataStatus<List<Vacancy>>> {
        return flow {
            emit(DataStatus.Loading())

            val result = networkClient.getSimilarVacancy(RequestWrapper(id))

            when (result.code) {
                200 -> {
                    result.data?.listVacancy?.let {
                        if (it.isEmpty()) emit(DataStatus.EmptyContent())
                        else{
                            val list = it.map { it -> converter.vacancyDtoToVacancy(it) }
                            emit(DataStatus.Content(list))
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
}