package ru.practicum.android.diploma.vacancy.data.impl

import android.content.Context
import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.hhApi.NetworkClient
import ru.practicum.android.diploma.hhApi.dto.RequestWrapper
import ru.practicum.android.diploma.search.domain.models.Vacancy
import ru.practicum.android.diploma.util.DataStatus
import ru.practicum.android.diploma.util.mappers.VacancyDtoMapper
import ru.practicum.android.diploma.vacancy.domain.repository.VacancyDetailsRepository

class VacancyDetailsRepositoryImpl(
    private val networkClient: NetworkClient,
    private val vacancyMapper: VacancyDtoMapper,
    private val context: Context,
) : VacancyDetailsRepository {

    override suspend fun getVacancyDetails(id: String): Flow<DataStatus<Vacancy>> = flow {

        val response = networkClient.getVacancyDetails(RequestWrapper(id))

        when (response.code) {
             NO_CONNECT-> {
                emit(DataStatus.NoConnecting())
            }

            SUCCESS -> {
                if(response.data != null){
                    val data = vacancyMapper.vacancyDtoToVacancy(response.data)
                    emit(DataStatus.Content(data))
                }
                else {
                    Log.e("ErrorGetData",R.string.error_get_data_empty.toString())
                }
               //Данный стейт говорит о пустой дате. Тут нужна логика по обработке пустых данных.
                //На экране ваканский такой логики нет, по-этому данный стэйт не должен запускаться
                //На всякий случай он уйдет в лог.

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
        const val NO_CONNECT = 0
        const val SUCCESS = 200
    }
}