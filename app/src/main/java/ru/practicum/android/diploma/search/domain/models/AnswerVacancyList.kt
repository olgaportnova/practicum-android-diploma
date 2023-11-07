package ru.practicum.android.diploma.search.domain.models

data class AnswerVacancyList(
    val found: Int,
    val maxPages:Int,
    val currentPages:Int,
    val listVacancy:List<Vacancy>
)
