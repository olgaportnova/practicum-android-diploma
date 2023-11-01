package ru.practicum.android.diploma.filter.domain.interfaces

import ru.practicum.android.diploma.filter.domain.models.Country
import ru.practicum.android.diploma.util.DataResource

interface AreaController {
    fun loadCountries():DataResource<String>
}