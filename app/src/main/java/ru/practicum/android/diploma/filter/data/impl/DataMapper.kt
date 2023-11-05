package ru.practicum.android.diploma.filter.data.impl

import ru.practicum.android.diploma.filter.data.impl.dto.ApiArea
import ru.practicum.android.diploma.filter.data.impl.dto.ApiCountry
import ru.practicum.android.diploma.filter.domain.models.Area

interface DtoConverter<In,Out>{
    fun convertFromDto(modelApi:In):Out
}
class CountryConverter():DtoConverter<ApiCountry,Area>{
    override fun convertFromDto(modelApi: ApiCountry): Area {
        return Area(
            name = modelApi.name,
            id = modelApi.id,
            parentId = 0,
            areas = emptyList()
        )
    }
}
class DistrictConverter():DtoConverter<ApiArea,Area>{
    override fun convertFromDto(modelApi: ApiArea): Area {
        val areas = modelApi.areas.map { convertFromDto(it) }
        return Area(modelApi.id, modelApi.parentId, modelApi.name, areas)
    }
}