package ru.practicum.android.diploma.sharedPref.impl

import ru.practicum.android.diploma.filter.data.impl.dto.ParamsFilterModelDto

interface FiltersStorage {

    fun getStateSavedFilters():Boolean

    fun getParamsFiltes():ParamsFilterModelDto
}