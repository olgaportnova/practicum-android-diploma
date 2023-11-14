package ru.practicum.android.diploma.filter.domain.interfaces

import android.provider.Settings
import ru.practicum.android.diploma.filter.domain.models.FilterData

interface FiltersRepository {
    fun getFiltersSet():FilterData?
    fun saveFiltersSet(settingsToSave:FilterData)
}