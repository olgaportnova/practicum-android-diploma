package ru.practicum.android.diploma.filter.domain.impl

import ru.practicum.android.diploma.filter.data.dto.models.FilterDto
import ru.practicum.android.diploma.filter.domain.interfaces.FiltersController
import ru.practicum.android.diploma.sharedPref.FiltersStorage

class FiltersControllerImpl(private val filterStorage:FiltersStorage):FiltersController {
    override fun getFilterSettings(): FilterDto? {
        return filterStorage.getParamsFilters()
    }

    override fun saveFilterSettings(filterToSave: FilterDto) {
        filterStorage.addParamsFilters(params = filterToSave)
    }
}