package ru.practicum.android.diploma.filter.domain.models

data class FilterData(
    var idCountry: String?,
    var idArea:String?,
    val idIndustry:String?,
    var nameCountry:String?,
    var nameArea: String?,
    val nameIndustry:String?,
    val currency:String?,
    val salary:Int?,
    val onlyWithSalary:Boolean,
)

