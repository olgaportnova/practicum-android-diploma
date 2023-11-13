package ru.practicum.android.diploma.vacancy.domain.repository

interface ExternalNavigator {
    fun shareVacancy(vacancyUrl: String)
    fun openEmail(email: String)
    fun makeCall(phone: String)
}