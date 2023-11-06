package ru.practicum.android.diploma.search.domain.models

data class VacancyForTests(
    val id: Int,
    val vacancyName: String,
    val companyName: String,
    val alternateUrl:String?,
    val logoUrl: String?,
    val city: String?,
    val employment: String?,
    val experience: String?,
    val salary: SalaryTest?,
    val description: String?,
    val keySkills: List<String?>,
    val contacts: ContactsTest?,
    val comment: String?
)

data class SalaryTest(
    val currency: String?,
    val from: Int?,
    val gross: Boolean?,
    val to: Int?
)

data class ContactsTest(
    val email: String?,
    val name: String?,
    val phones: List<PhoneTest?>?
)

data class PhoneTest(
    val city: String?,
    val comment: String?,
    val country: String?,
    val number: String?
)