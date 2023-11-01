package ru.practicum.android.diploma.filter.data.impl.pojo

import com.google.gson.annotations.SerializedName

data class CountryW(
    @SerializedName("name")
    val name: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("parentId")
    val parentId: Int?,
    @SerializedName("areas")
    val areas: List<ApiArea>
)