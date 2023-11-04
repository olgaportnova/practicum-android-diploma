package ru.practicum.android.diploma.search.data.impl.dto.models

import com.google.gson.annotations.SerializedName

data class AnswrVacancyListDto(
    val found: Int,
    @SerializedName("pages")
    val maxPages:Int,
    @SerializedName("page")
    val currentPages:Int,
    @SerializedName("items")
    val listVacancy:List<VacancyDto>
)
