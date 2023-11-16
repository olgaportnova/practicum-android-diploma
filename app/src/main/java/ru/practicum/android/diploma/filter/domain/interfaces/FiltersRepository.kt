package ru.practicum.android.diploma.filter.domain.interfaces

import ru.practicum.android.diploma.filter.domain.models.FilterData

interface FiltersRepository {
    fun getFiltersSet(): FilterData
    fun getDefaultSettings(): FilterData
    fun saveFiltersSet(settingsToSave: FilterData)
}