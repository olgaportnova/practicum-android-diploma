package ru.practicum.android.diploma.filter.data.impl

import ru.practicum.android.diploma.filter.data.impl.pojo.ApiArea
import ru.practicum.android.diploma.filter.data.impl.pojo.CountryW
import ru.practicum.android.diploma.filter.domain.models.Area
import ru.practicum.android.diploma.filter.domain.models.Country

class DataMapper {
    fun countryFromPojo(apiCountry: CountryW): Country {
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