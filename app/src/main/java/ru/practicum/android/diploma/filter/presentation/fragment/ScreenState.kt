package ru.practicum.android.diploma.filter.presentation.fragment

import ru.practicum.android.diploma.filter.domain.models.AreaData

sealed class ScreenState {
    data class Content(val data: List<AreaData>) : ScreenState()
    data class Loading(val code: Any?) : ScreenState()
    data class Error(val exception: String) : ScreenState()
}