package ru.practicum.android.diploma.filter.data.impl

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import ru.practicum.android.diploma.filter.domain.interfaces.AreaRepository
import ru.practicum.android.diploma.filter.domain.models.Area
import ru.practicum.android.diploma.hhApi.impl.NetworkClientImpl
import ru.practicum.android.diploma.util.DataStatus

class AreaRepositoryImpl(private val networkClient: NetworkClientImpl) : AreaRepository {
    override suspend fun loadCountries(): Flow<DataStatus<List<Area>>> {
        return flow {
            emit(DataStatus.Loading())

            val result = networkClient.getAreas()

            when (result.code) {
                200 -> {
                    result.data.let {
                        //Вот тут я указал, что точно не null. Ну можно делать доп. проверку на null вначале.
                        val lst = it!!.map { el -> DataMapper().apiCountryToArea(el) }
                        emit(DataStatus.Content(lst))
                    }
                }
                //можено еще добвать код для обрабоки ошибки подключения сети- 0

                else -> emit(DataStatus.Error(result.code))
            }
        }
            .catch { emit(DataStatus.Error(errorMessage = it.message.toString())) }
            .flowOn(Dispatchers.IO)
    }

    override suspend fun loadDistricts(parentId: Int): Flow<DataStatus<Area>> {
        return flow {
            emit(DataStatus.Loading()) // Отправка информации о старте загрузки

            val result = networkClient.getDistricts(parentId)

            when (result.code) {
                200 -> {
                    emit(DataStatus.Content(DataMapper().apiAreaToArea(result.data!!)))
                }
            }
        }
            .catch { Log.e("LOG", it.message.toString()) }
            .flowOn(Dispatchers.IO)
    }
}