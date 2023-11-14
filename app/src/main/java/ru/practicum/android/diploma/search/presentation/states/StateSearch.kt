package ru.practicum.android.diploma.search.presentation.states

import ru.practicum.android.diploma.search.domain.models.AnswerVacancyList

sealed class StateSearch {
    object Default : StateSearch()
    object NoConnecting : StateSearch()
    data class Error(val code: Int) : StateSearch()
    object EmptyContent : StateSearch()
    data class Content(val data: AnswerVacancyList) : StateSearch()
    object Loading : StateSearch()
}