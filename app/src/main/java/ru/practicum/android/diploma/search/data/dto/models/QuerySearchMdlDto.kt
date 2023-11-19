package ru.practicum.android.diploma.search.data.dto.models

data class QuerySearchMdlDto(
    val page:Int,
    val perPage:Int,
    val text:String,
    val area:String? = null,
    val parentArea:String? = null,
    val industry:String? = null,
    val currency:String? = null,
    val salary:Int? = null,
    val onlyWithSalary:Boolean = false,
)
