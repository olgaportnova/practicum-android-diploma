package ru.practicum.android.diploma.filter.domain.interfaces

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.filter.domain.models.Area
import ru.practicum.android.diploma.filter.domain.models.Country
import ru.practicum.android.diploma.filter.network.DataStatus
import ru.practicum.android.diploma.util.DataResource

interface AreaRepository {
    suspend fun loadCountries(): Flow<DataStatus<List<Country>>>

    suspend fun loadDistricts(parentId:Int): Flow<DataStatus<List<Area>>>
}