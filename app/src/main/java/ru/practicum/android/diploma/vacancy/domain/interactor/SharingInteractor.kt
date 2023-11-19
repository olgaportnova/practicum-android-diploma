package ru.practicum.android.diploma.vacancy.domain.interactor

import ru.practicum.android.diploma.search.domain.models.Phone

interface SharingInteractor {
    fun shareVacancy(vacancyUrl: String)
    fun openEmail(email: String)
    fun makeCall(phone: Phone)
}