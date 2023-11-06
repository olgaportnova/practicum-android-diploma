package ru.practicum.android.diploma.search.domain.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.hhApi.impl.NetworkClientImpl
import ru.practicum.android.diploma.search.data.dto.models.QuerySearchMdlDto
import ru.practicum.android.diploma.search.data.impl.SearchRepositoryImpl
import ru.practicum.android.diploma.search.domain.SearchInteractor
import ru.practicum.android.diploma.search.domain.SearchRepositry
import ru.practicum.android.diploma.search.domain.models.AnswerVacancyList
import ru.practicum.android.diploma.search.domain.models.QuerySearchMdl
import ru.practicum.android.diploma.util.mappers.QuerySearchMapper

class SearchInteractorImpl(private val searchRepository: SearchRepositry) : SearchInteractor {

    val testModelRequest = QuerySearchMdl(
        0,
        perPage = 20,
        text = "разработчик",
    )

suspend fun getTestRequest(request: QuerySearchMdl) : Flow<AnswerVacancyList> {
    return searchRepository.doRequestSearch(request)
}
}