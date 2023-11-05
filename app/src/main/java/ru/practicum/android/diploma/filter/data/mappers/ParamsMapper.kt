package ru.practicum.android.diploma.filter.data.mappers

import ru.practicum.android.diploma.filter.data.dto.FilterDto
import ru.practicum.android.diploma.filter.domain.models.FilterData

object ParamsMapper {

    fun paramsModelInParamsModelDto(model:FilterData): FilterDto {
        return FilterDto(
            idCountry = model.idCountry,
            idArea =  model.idArea,
            idIndustry = model.idIndustry,
            nameCountry = model.nameCountry,
            nameArea = model.nameArea,
            nameIndustry = model.nameIndustry,
            currency = model.currency,
            salary = model.salary,
            onlyWithSalary = model.onlyWithSalary

        )
    }

    fun paramsModelDtoInParamsModel(model: FilterDto):FilterData{
        return FilterData(
            idCountry = model.idCountry,
            idArea =  model.idArea,
            idIndustry = model.idIndustry,
            nameCountry = model.nameCountry,
            nameArea = model.nameArea,
            nameIndustry = model.nameIndustry,
            currency = model.currency,
            salary = model.salary,
            onlyWithSalary = model.onlyWithSalary

        )
    }
}