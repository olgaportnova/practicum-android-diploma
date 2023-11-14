package ru.practicum.android.diploma.filter.data.dto.models

import com.google.gson.annotations.SerializedName

data class AreaDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("parentId")
    val parentId: Int?,
    @SerializedName("name")
    val name: String,
    @SerializedName("areas")
    val areas: List<AreaDto>
)