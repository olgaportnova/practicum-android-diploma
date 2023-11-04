package ru.practicum.android.diploma.filter.data.impl.dto

import ru.practicum.android.diploma.filter.domain.models.ParamsFilterModel

object ParamsMapper {

    fun paramsModelInParamsModelDto(model:ParamsFilterModel): ParamsFilterModelDto {
        return ParamsFilterModelDto(
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

    fun paramsModelDtoInParamsModel(model:ParamsFilterModelDto):ParamsFilterModel{
        return ParamsFilterModel(
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