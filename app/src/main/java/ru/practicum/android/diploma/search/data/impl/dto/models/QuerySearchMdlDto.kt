package ru.practicum.android.diploma.search.data.impl.dto.models

data class QuerySearchMdlDto(
    val page:Int,
    val perPage:Int,
    val text:String,
    val area:String?,
    val parentArea:String?,
    val industry:String?,
    //С данным полем есть вопросы. Необходимо ли оно в дальнейшем или нет.
    val currency:String?,
    val salary:Int?,
    val onlyWithSalary:Boolean,
)
