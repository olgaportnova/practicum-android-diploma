package ru.practicum.android.diploma.filter.data.impl

import ru.practicum.android.diploma.filter.data.impl.dto.ApiArea
import ru.practicum.android.diploma.filter.data.impl.dto.ApiCountry
import ru.practicum.android.diploma.filter.domain.models.Area

class DataMapper {
    fun apiCountryToArea(apiCountry: ApiCountry): Area {
        return Area(
            name = apiCountry.name,
            id = apiCountry.id,
            parentId = 0,
            areas = emptyList()
        )
    }

    fun apiAreaToArea(apiArea: ApiArea): Area {
            val areas = apiArea.areas.map { apiAreaToArea(it) }
            return Area(apiArea.id, apiArea.parentId, apiArea.name, areas)
        }

}