package ru.practicum.android.diploma.vacancy.data.dto

import ru.practicum.android.diploma.hhApi.dto.ResponseWrapper
import ru.practicum.android.diploma.search.data.dto.models.VacancyDto

class VacancyDetailsResponse (val result: VacancyDto) : ResponseWrapper<VacancyDto>(data = result)