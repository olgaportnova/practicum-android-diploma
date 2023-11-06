package ru.practicum.android.diploma.search.data.dto.models

import com.google.gson.annotations.SerializedName
data class VacancyDtoTest(
    val id: Int,
    @SerializedName("name")
    val vacancyName: String,
    @SerializedName("alternate_url")
    val alternateUrl:String?,
    val employer: EmployerDtoTest,
    @SerializedName("logo_urls")
    val logo: LogoDtoTest?,
    val area: AreaDtoTest,
    val employment: EmploymentDtoTest?, // это тип занятости
    val experience: ExperienceDtoTest?,
    val salary: SalaryDtoTest?,
    val description: String?,
    @SerializedName("key_skills")
    val keySkills: List<KeySkillDtoTest>?,
    val contacts: ContactsDtoTest?,
    val comment: String?
)

data class SalaryDtoTest(
    val currency: String?,
    val from: Int?,
    val gross: Boolean?,
    val to: Int?
)

data class ContactsDtoTest(
    val email: String?,
    val name: String?,
    val phones: List<PhoneDtoTest?>?
)

data class PhoneDtoTest(
    val city: String?,
    val comment: String?,
    val country: String?,
    val number: String?
)

data class ExperienceDtoTest(
    val name: String?
)

data class EmploymentDtoTest(
    val name: String?
)

data class AreaDtoTest(
    val name: String?
)

data class EmployerDtoTest(
    val name: String
)

data class KeySkillDtoTest(
    val name: String?
)

data class LogoDtoTest(
    @SerializedName("90")
    val small: String?,
    @SerializedName("240")
    val big: String?,
    @SerializedName("original")
    val original: String?
)

