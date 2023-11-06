package ru.practicum.android.diploma.filter.presentation.util

import ru.practicum.android.diploma.filter.domain.models.AbstarctData

sealed class ScreenState {
    data class Content(val data: List<AbstarctData>) : ScreenState()
    data class Loading(val code: Any?) : ScreenState()
    data class Error(val exception: String) : ScreenState()
}