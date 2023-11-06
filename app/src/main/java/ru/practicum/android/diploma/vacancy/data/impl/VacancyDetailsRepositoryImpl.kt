package ru.practicum.android.diploma.vacancy.data.impl

import android.content.Context
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.hhApi.NetworkClient
import ru.practicum.android.diploma.search.domain.models.Vacancy
import ru.practicum.android.diploma.util.DataStatus
import ru.practicum.android.diploma.util.mappers.VacancyDtoMapper
import ru.practicum.android.diploma.vacancy.data.dto.VacancyDetailsRequest
import ru.practicum.android.diploma.vacancy.data.dto.VacancyDetailsResponse
import ru.practicum.android.diploma.vacancy.domain.repository.VacancyDetailsRepository

class VacancyDetailsRepositoryImpl(
    private val networkClient: NetworkClient,
    private val vacancyMapper: VacancyDtoMapper,
    private val context: Context,
) : VacancyDetailsRepository {

    override suspend fun getVacancyDetails(id: String): Flow<DataStatus<Vacancy>> = flow {

        val response = networkClient.getVacancyDetails(VacancyDetailsRequest(id))

        when (response.code) {
            ERROR -> {
                emit(DataStatus.NoConnecting())
            }

            SUCCESS -> {
                with(response as VacancyDetailsResponse) {
                    val data = vacancyMapper.vacancyDtoToVacancy(result)
                    emit(DataStatus.Content(data))
                }
            }

            else -> {
                emit(
                    DataStatus.Error(
                        response.code,
                        context.getString(R.string.server_error)
                    )
                )
            }

        }
    }

    companion object {
        const val ERROR = 0
        const val SUCCESS = 200
    }
}