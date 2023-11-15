package ru.practicum.android.diploma.filter.presentation.fragment

import ru.practicum.android.diploma.filter.domain.models.FilterData

sealed class ScreenWorkPlaceState {
    data class Content(
        val filterSet: FilterData,
        val btnAcceptVisibility: Boolean
    ) : ScreenWorkPlaceState()

    data class Loading(val code: Any?) : ScreenWorkPlaceState()
}