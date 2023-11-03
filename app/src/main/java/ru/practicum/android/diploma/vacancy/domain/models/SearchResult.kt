package ru.practicum.android.diploma.vacancy.domain.models

import java.io.Serializable

data class SearchResult<out A, out B>(
    val resultList: A,
    val error: B
) : Serializable
