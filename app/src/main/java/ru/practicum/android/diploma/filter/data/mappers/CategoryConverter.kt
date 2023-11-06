package ru.practicum.android.diploma.filter.data.mappers

import ru.practicum.android.diploma.filter.data.dto.models.CategoryDto
import ru.practicum.android.diploma.filter.domain.models.CategoryData

class CategoryConverter : DtoConverter<CategoryDto, CategoryData> {
    override fun convertFromDto(modelApi: CategoryDto): CategoryData {
        return CategoryData(
            id = modelApi.id,
            name = modelApi.name,
            roles = modelApi.roles.map { RoleConverter().convertFromDto(it) }
        )
    }
}