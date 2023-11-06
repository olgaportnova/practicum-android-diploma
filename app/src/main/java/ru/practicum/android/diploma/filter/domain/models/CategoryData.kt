package ru.practicum.android.diploma.filter.domain.models

data class CategoryData(
    val id: Int,
    val name: String,
    val roles: List<RoleData>
)
