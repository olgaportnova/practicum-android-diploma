package ru.practicum.android.diploma.similar.domain.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.search.domain.SearchInteractor
import ru.practicum.android.diploma.search.domain.SearchRepository
import ru.practicum.android.diploma.search.domain.models.AnswerVacancyList
import ru.practicum.android.diploma.search.domain.models.QuerySearchMdl
import ru.practicum.android.diploma.search.domain.models.Vacancy
import ru.practicum.android.diploma.similar.domain.SimilarInteractor
import ru.practicum.android.diploma.similar.domain.SimilarRepository
import ru.practicum.android.diploma.util.DataStatus

class SimilarInteractorImpl(
    private val similarRepository: SimilarRepository) : SimilarInteractor {

    override suspend fun getSimilarVacancies(id:String): Flow<DataStatus<List<Vacancy>>> {
        return similarRepository.getSimilarVacancies(id)
    }

}