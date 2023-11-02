package ru.practicum.android.diploma.filter.domain.models
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Area(
    val id: Int,
    val parentId: Int?,
    val name: String,
    val areas: List<Area>
):Parcelable