package ru.practicum.android.diploma.search.presentation.states

import ru.practicum.android.diploma.filter.domain.models.FilterData

sealed class StateFilters {
    data class UseFilters(val content: FilterData) : StateFilters()
    object NoUseFilters : StateFilters()
}