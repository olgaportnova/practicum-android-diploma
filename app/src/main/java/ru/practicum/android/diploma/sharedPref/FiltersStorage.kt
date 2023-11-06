package ru.practicum.android.diploma.sharedPref

import ru.practicum.android.diploma.filter.data.dto.models.FilterDto

interface FiltersStorage {

    fun getParamsFilters(): FilterDto?

    fun addParamsFilters(params: FilterDto)
}