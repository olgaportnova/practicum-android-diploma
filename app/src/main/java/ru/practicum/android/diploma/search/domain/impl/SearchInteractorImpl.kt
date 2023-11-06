package ru.practicum.android.diploma.search.domain.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.search.domain.SearchInteractor
import ru.practicum.android.diploma.search.domain.SearchRepositry
import ru.practicum.android.diploma.search.domain.models.AnswerVacancyList
import ru.practicum.android.diploma.search.domain.models.QuerySearchMdl

class SearchInteractorImpl(private val searchRepository: SearchRepositry) : SearchInteractor {


suspend override fun  getTestRequest(request: QuerySearchMdl) : Flow<AnswerVacancyList> {
    return searchRepository.doRequestSearch(request)
}
}