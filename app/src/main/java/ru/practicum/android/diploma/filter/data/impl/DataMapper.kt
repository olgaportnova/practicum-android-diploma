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
            areas = apiCountry.areas.map { areaFromPojo(it) }
        )
    }

    fun areaFromPojo(apiArea: ApiArea): Area {
        return Area(
            id = apiArea.id,
            name = apiArea.name,
            parentId = apiArea.parentId,
            areas = apiArea.areas.map {
                this.areaFromPojo(it)
            }
        )
    }
}