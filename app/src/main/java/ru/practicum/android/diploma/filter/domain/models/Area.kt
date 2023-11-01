package ru.practicum.android.diploma.filter.domain.models

data class Area(
    val id: Int,
    val parentId: Int?,
    val name: String,
    val areas: List<Area>
)