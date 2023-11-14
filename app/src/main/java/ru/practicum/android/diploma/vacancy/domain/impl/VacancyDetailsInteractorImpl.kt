package ru.practicum.android.diploma.vacancy.domain.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.search.domain.models.Vacancy
import ru.practicum.android.diploma.util.DataStatus
import ru.practicum.android.diploma.vacancy.domain.interactor.VacancyDetailsInteractor
import ru.practicum.android.diploma.vacancy.domain.repository.VacancyDetailsRepository

class VacancyDetailsInteractorImpl(private val repository: VacancyDetailsRepository) :
    VacancyDetailsInteractor {

    override suspend fun getVacancyDetails(id: String): Flow<DataStatus<Vacancy>> {
        return repository.getVacancyDetails(id)
    }

}