package ru.practicum.android.diploma.filter.domain.interfaces

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.filter.domain.models.Area
import ru.practicum.android.diploma.util.DataStatus

interface AreaRepository {
    suspend fun loadCountries(): Flow<DataStatus<List<Area>>>

    suspend fun loadDistricts(parentId: Int): Flow<DataStatus<Area>>
}