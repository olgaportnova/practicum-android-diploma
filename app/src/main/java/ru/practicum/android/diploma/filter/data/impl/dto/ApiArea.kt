package ru.practicum.android.diploma.filter.data.impl.dto

import com.google.gson.annotations.SerializedName

data class ApiArea(
    @SerializedName("id")
    val id: Int,
    @SerializedName("parentId")
    val parentId: Int?,
    @SerializedName("name")
    val name: String,
    @SerializedName("areas")
    val areas: List<ApiArea>
)

data class ApiDistrictSearch(
    val name: String,
    val id: String,
    val parent_id: String?,
)
data class CountryResponse(
    val countries: List<ApiDistrictSearch>
)
