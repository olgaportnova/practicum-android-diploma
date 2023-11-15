package ru.practicum.android.diploma.filter.domain.interfaces

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.filter.domain.models.AreaData
import ru.practicum.android.diploma.util.DataStatus


interface AreaController {
    suspend fun loadCountries(): Flow<DataStatus<List<AreaData>>>
    suspend fun loadDistricts(parentId: Int): Flow<DataStatus<AreaData>>

    suspend fun loadAreaTree():Flow<DataStatus<List<AreaData>>>
}