package ru.practicum.android.diploma.vacancy.domain.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.practicum.android.diploma.search.domain.models.Vacancy
import ru.practicum.android.diploma.util.DataStatus
import ru.practicum.android.diploma.vacancy.domain.interactor.VacancyDetailsInteractor
import ru.practicum.android.diploma.vacancy.domain.models.SearchResult
import ru.practicum.android.diploma.vacancy.domain.repository.VacancyDetailsRepository

class VacancyDetailsInteractorImpl(private val repository: VacancyDetailsRepository) :
    VacancyDetailsInteractor {

    override suspend fun getVacancyDetails(id: String): Flow<SearchResult<Vacancy?, Int?>> {
        return repository.getVacancyDetails(id).map { result ->
            when (result) {
                is DataStatus.Content -> {
                    SearchResult(result = result.data, error = null)
                }

                is DataStatus.Error -> {
                    SearchResult(result = null, error = result.code)
                }

                else -> {
                    throw IllegalArgumentException("Unknown DataStatus type")
                }
            }
        }
    }

}

