package ru.practicum.android.diploma.filter.domain.interfaces

import ru.practicum.android.diploma.util.DataResource

interface AreaRepository {
    suspend fun loadCountries(): DataResource<String>
}