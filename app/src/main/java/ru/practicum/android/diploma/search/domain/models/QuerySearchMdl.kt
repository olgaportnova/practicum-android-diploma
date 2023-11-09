package ru.practicum.android.diploma.search.domain.models

data class QuerySearchMdl(
    var page:Int,
    val perPage:Int,
    var text:String,
    val area:String? = null,
    val parentArea:String? = null,
    val industry:String? = null,
    //С данным полем есть вопросы. Необходимо ли оно в дальнейшем или нет.
    val currency:String? = null,
    val salary:Int? = null,
    val onlyWithSalary:Boolean = false
)
