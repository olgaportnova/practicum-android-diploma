package ru.practicum.android.diploma.search.domain.models

import ru.practicum.android.diploma.search.data.dto.models.VacancyDto

data class AnswerVacancyList(
    val found: Int,
    val maxPages:Int,
    val currentPages:Int,
    val listVacancy:List<Vacancy>
)
