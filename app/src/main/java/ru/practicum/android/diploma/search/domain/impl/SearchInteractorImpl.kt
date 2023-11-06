package ru.practicum.android.diploma.search.domain.impl

import ru.practicum.android.diploma.hhApi.impl.NetworkClientImpl
import ru.practicum.android.diploma.search.data.dto.models.QuerySearchMdlDto
import ru.practicum.android.diploma.search.data.impl.SearchRepositoryImpl
import ru.practicum.android.diploma.search.domain.SearchInteractor
import ru.practicum.android.diploma.search.domain.SearchRepositry

class SearchInteractorImpl(private val searchRepository: SearchRepositry) : SearchInteractor {

    val testModelRequest = QuerySearchMdlDto(
        0,
        perPage = 20,
        text = "разработчик",
    )

}