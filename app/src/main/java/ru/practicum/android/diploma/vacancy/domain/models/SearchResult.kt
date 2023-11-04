package ru.practicum.android.diploma.vacancy.domain.models

data class SearchResult<out A, out B>(
    val result: A,
    val error: B
)
