package ru.practicum.android.diploma.filter.data.impl.mappers

import ru.practicum.android.diploma.filter.data.impl.dto.CountryDto
import ru.practicum.android.diploma.filter.domain.models.AreaData

class CountryConverter(): DtoConverter<CountryDto, AreaData> {
    override fun convertFromDto(modelApi: CountryDto): AreaData {
        return AreaData(
            name = modelApi.name,
            id = modelApi.id,
            parentId = 0,
            areas = emptyList()
        )
    }
}