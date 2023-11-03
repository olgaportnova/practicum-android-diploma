package ru.practicum.android.diploma.vacancy.domain.models

data class VacancyDetails(
    val id: Int,
    val vacancyName: String,
    val companyName: String,
    val logoUrl: String?,
    val city: String?,
    val employment: String?,
    val experience: String?,
    val salary: Salary?,
    val description: String?,
    val keySkills: List<String?>,
    val contacts: Contacts?,
    val comment: String?,
    val isFavorite: Boolean
)

data class Salary(
    val currency: String?,
    val from: Int?,
    val gross: Boolean?,
    val to: Int?
)

data class Contacts(
    val email: String?,
    val name: String?,
    val phones: Phone?
)

data class Phone(
    val city: String?,
    val comment: String?,
    val country: String?,
    val number: String?
)