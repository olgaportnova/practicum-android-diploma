package ru.practicum.android.diploma.filter.data.dto.models

import com.google.gson.annotations.SerializedName

data class RoleDto(
    @SerializedName("accept_incomplete_resumes")
    val acceptIncompleteResumes: Boolean,
    val id: String,
    @SerializedName("is_default")
    val isDefault: Boolean,
    val name: String
)
