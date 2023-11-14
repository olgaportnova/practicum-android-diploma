package ru.practicum.android.diploma.filter.data.dto.models

import com.google.gson.annotations.SerializedName

data class CountryDto(
    @SerializedName("name")
    val name: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("url")
    val url: String,
)