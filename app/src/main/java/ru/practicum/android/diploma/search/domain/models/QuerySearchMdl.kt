package ru.practicum.android.diploma.search.domain.models

data class QuerySearchMdl(
    var page:Int,
    var perPage:Int,
    var text:String,
    var area:String? = null,
    var parentArea:String? = null,
    var industry:String? = null,
    //С данным полем есть вопросы. Необходимо ли оно в дальнейшем или нет.
    var currency:String? = null,
    var salary:Int? = null,
    var onlyWithSalary:Boolean = false
)
