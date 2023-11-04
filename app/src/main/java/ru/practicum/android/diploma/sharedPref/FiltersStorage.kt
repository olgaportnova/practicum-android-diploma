package ru.practicum.android.diploma.sharedPref

import ru.practicum.android.diploma.filter.data.impl.dto.ParamsFilterModelDto

interface FiltersStorage {

    fun getParamsFilters():ParamsFilterModelDto?

    fun addParamsFilters(params:ParamsFilterModelDto)
}