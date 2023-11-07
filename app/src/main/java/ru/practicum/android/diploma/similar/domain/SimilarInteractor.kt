package ru.practicum.android.diploma.similar.domain

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.search.domain.models.Vacancy
import ru.practicum.android.diploma.util.DataStatus

interface SimilarInteractor {

    suspend fun getSimilarVacancies(id:String): Flow<DataStatus<List<Vacancy>>>

}