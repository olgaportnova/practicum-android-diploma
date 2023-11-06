package ru.practicum.android.diploma.util.mappers

import ru.practicum.android.diploma.search.data.dto.models.QuerySearchMdlDto
import ru.practicum.android.diploma.search.domain.models.QuerySearchMdl

object QuerySearchMapper {

    fun qrSearchMdlToQrSearchMdlDto(model:QuerySearchMdl): QuerySearchMdlDto{
        return with(model){
            QuerySearchMdlDto(
                page = page,
                perPage = perPage,
                text = text,
                area = area,
                parentArea = parentArea,
                industry = industry,
                currency = currency,
                salary = salary,
                onlyWithSalary = onlyWithSalary

            )
        }
    }
}