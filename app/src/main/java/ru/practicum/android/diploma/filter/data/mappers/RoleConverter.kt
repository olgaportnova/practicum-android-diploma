package ru.practicum.android.diploma.filter.data.mappers

import ru.practicum.android.diploma.filter.data.dto.models.RoleDto
import ru.practicum.android.diploma.filter.domain.models.RoleData

class RoleConverter:DtoConverter<RoleDto,RoleData> {
    override fun convertFromDto(modelApi: RoleDto): RoleData {
        return RoleData(
            acceptIncompleteResumes = modelApi.acceptIncompleteResumes,
            id = modelApi.id,
            isDefault = modelApi.isDefault,
            name = modelApi.name
        )
    }
}