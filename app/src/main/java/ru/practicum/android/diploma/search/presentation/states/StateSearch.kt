package ru.practicum.android.diploma.search.presentation.states

import ru.practicum.android.diploma.filter.domain.models.FilterData
import ru.practicum.android.diploma.search.domain.models.AnswerVacancyList

sealed class StateSearch {
    object NoneMessage : StateSearch()
    class ShowMessage(val message: String) : StateSearch()
    object Default : StateSearch()
    object NoConnecting : StateSearch()
    data class Error(val code: Int) : StateSearch()
    object EmptyContent : StateSearch()
    data class Content(val data: AnswerVacancyList) : StateSearch()
    object Loading : StateSearch()
    data class UseFilters(val content: FilterData) : StateSearch()
    object NoUseFilters : StateSearch()
}