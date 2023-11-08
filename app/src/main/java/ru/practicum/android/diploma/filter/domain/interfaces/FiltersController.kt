package ru.practicum.android.diploma.filter.domain.interfaces

import ru.practicum.android.diploma.filter.domain.models.FilterData

interface FiltersController {
    fun getFilterSettings(): FilterData?
    fun saveFilterSettings(filterToSave: FilterData)
}