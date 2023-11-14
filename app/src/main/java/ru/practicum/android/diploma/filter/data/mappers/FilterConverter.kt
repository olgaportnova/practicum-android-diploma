package ru.practicum.android.diploma.filter.data.mappers

import ru.practicum.android.diploma.filter.data.dto.models.FilterDto
import ru.practicum.android.diploma.filter.domain.models.FilterData

class FilterConverter(): DtoConverter<FilterDto, FilterData> {
    override fun convertFromDto(modelApi: FilterDto): FilterData {
        return FilterData(
            idCountry = modelApi.idCountry,
            idArea =  modelApi.idArea,
            idIndustry = modelApi.idIndustry,
            nameCountry = modelApi.nameCountry,
            nameArea = modelApi.nameArea,
            nameIndustry = modelApi.nameIndustry,
            currency = modelApi.currency,
            salary = modelApi.salary,
            onlyWithSalary = modelApi.onlyWithSalary
        )
    }
    fun convertToDto(modelData: FilterData): FilterDto {
        return FilterDto(
            idCountry = modelData.idCountry,
            idArea =  modelData.idArea,
            idIndustry = modelData.idIndustry,
            nameCountry = modelData.nameCountry,
            nameArea = modelData.nameArea,
            nameIndustry = modelData.nameIndustry,
            currency = modelData.currency,
            salary = modelData.salary,
            onlyWithSalary = modelData.onlyWithSalary
        )
    }
}