package ru.practicum.android.diploma.filter.data.repository

import ru.practicum.android.diploma.filter.domain.interfaces.FiltersRepository
import ru.practicum.android.diploma.filter.domain.models.FilterData
import ru.practicum.android.diploma.sharedPref.FiltersStorage

class FiltersRepositoryImpl(private val sharedPrefsClient:FiltersStorage):FiltersRepository {
    override fun getFiltersSet(): FilterData? {
        return if (sharedPrefsClient.getParamsFilters()==null){
            null
        } else null
    }

    override fun saveFiltersSet(settingsToSave: FilterData) {
        //sharedPrefsClient.addParamsFilters
    }
}