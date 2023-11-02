package ru.practicum.android.diploma.filter.data.impl

import ru.practicum.android.diploma.filter.data.impl.dto.ApiArea
import ru.practicum.android.diploma.filter.data.impl.dto.ApiCountry
import ru.practicum.android.diploma.filter.domain.models.Area
import ru.practicum.android.diploma.filter.domain.models.Country

class DataMapper {
    fun countryFromPojo(apiCountry: ApiCountry): Country {
        return Country(
            name = apiCountry.name,
            id = apiCountry.id,
            url = apiCountry.url
        )
    }

    fun convert(apiArea: ApiArea): Area {
            val areas = apiArea.areas.map { convert(it) }
            return Area(apiArea.id, apiArea.parentId, apiArea.name, areas)
        }

}