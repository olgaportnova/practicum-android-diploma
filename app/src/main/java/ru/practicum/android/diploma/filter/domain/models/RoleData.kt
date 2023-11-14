package ru.practicum.android.diploma.filter.domain.models

data class RoleData(
    val acceptIncompleteResumes: Boolean,
    val id: Int,
    val isDefault: Boolean,
    val name: String
)
