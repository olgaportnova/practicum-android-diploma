package ru.practicum.android.diploma.vacancy.domain.impl

import ru.practicum.android.diploma.search.domain.models.Phone
import ru.practicum.android.diploma.util.PhonesUtil
import ru.practicum.android.diploma.vacancy.domain.interactor.SharingInteractor
import ru.practicum.android.diploma.vacancy.domain.repository.ExternalNavigator

class SharingInteractorImpl(
    private val externalNavigator: ExternalNavigator,
) : SharingInteractor {

    override fun shareVacancy(vacancyUrl: String) {
        externalNavigator.shareVacancy(vacancyUrl)
    }

    override fun openEmail(email: String) {
        externalNavigator.openEmail(email)
    }

    override fun makeCall(phone: Phone) {
        val formatNumber = PhonesUtil.numberFormatting(phone)
        externalNavigator.makeCall(formatNumber)
    }
}