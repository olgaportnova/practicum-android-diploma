package ru.practicum.android.diploma.filter.domain.models
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AreaData(
    val id: Int,
    val parentId: Int?,
    val name: String,
    val areas: List<AreaData>
): Parcelable

data class AbstarctData(
    val id:Int,
    val name: String
)

