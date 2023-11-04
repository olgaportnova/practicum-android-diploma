package ru.practicum.android.diploma.search.domain.models

import ru.practicum.android.diploma.search.data.impl.dto.models.VacancyDto

data class AnswrVacancyList(
    val found: Int,
    val maxPages:Int,
    val currentPages:Int,
    val listVacancy:List<VacancyDto>
)
