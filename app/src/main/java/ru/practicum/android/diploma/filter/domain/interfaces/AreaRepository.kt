package ru.practicum.android.diploma.filter.domain.interfaces

import ru.practicum.android.diploma.util.DataResource

interface AreaRepository {
    fun loadCountries(): DataResource<String>
}