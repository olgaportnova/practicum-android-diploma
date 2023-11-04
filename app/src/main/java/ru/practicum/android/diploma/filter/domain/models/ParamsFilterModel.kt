package ru.practicum.android.diploma.filter.domain.models

data class ParamsFilterModel(
    val idCountry: String?,
    val idArea:String?,
    val idIndustry:String?,
    val nameCountry:String?,
    val nameArea: String?,
    val nameIndustry:String?,
    val currency:String?,
    val salary:Int?,
    val onlyWithSalary:Boolean,
)

