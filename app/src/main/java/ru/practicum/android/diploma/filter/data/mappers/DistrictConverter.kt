package ru.practicum.android.diploma.filter.data.mappers

import ru.practicum.android.diploma.filter.data.dto.models.AreaDto
import ru.practicum.android.diploma.filter.domain.models.AreaData

class DistrictConverter(): DtoConverter<AreaDto, AreaData> {
    override fun convertFromDto(modelApi: AreaDto): AreaData {
        val areas = modelApi.areas.map { convertFromDto(it) }
        return AreaData(modelApi.id, modelApi.parentId, modelApi.name, areas)
    }
}