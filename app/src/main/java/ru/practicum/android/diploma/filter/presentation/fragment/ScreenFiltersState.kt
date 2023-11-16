package ru.practicum.android.diploma.filter.presentation.fragment

import ru.practicum.android.diploma.filter.domain.models.FilterData

sealed class ScreenFiltersState {
    data class Content(
        val filterSet: FilterData,
        val btnAcceptVisibility: Boolean,
        val btnDeclineVisibility: Boolean
    ) : ScreenFiltersState()

    data class Loading(val code: Any?) : ScreenFiltersState()
}