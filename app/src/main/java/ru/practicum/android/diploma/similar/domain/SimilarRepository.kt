package ru.practicum.android.diploma.similar.domain

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.search.data.dto.models.VacancyDto
import ru.practicum.android.diploma.search.domain.models.Vacancy
import ru.practicum.android.diploma.util.DataStatus

interface SimilarRepository {

    suspend fun getSimilarVacancies(id: String) : Flow<DataStatus<List<Vacancy>>>
}