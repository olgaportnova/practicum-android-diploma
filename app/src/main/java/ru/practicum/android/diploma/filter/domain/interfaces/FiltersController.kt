package ru.practicum.android.diploma.filter.domain.interfaces

import ru.practicum.android.diploma.filter.data.dto.models.FilterDto

interface FiltersController {
    fun getFilterSettings():FilterDto?
    fun saveFilterSettings(filterToSave:FilterDto)
}