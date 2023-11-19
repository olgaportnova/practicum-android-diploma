package ru.practicum.android.diploma.filter.domain.models

data class AbstractData(
    val id:Int,
    val name: String,
    var isSelected:Boolean = false,
    val parentId:Int? = null
)