package ru.practicum.android.diploma.filter.presentation.util

import ru.practicum.android.diploma.filter.domain.models.AbstractData
import ru.practicum.android.diploma.filter.domain.models.FilterData

sealed class ScreenState {
    data class Content(val data: List<AbstractData>) : ScreenState()
    data class Loading(val code: Any?) : ScreenState()
    data class Error(val exception: String) : ScreenState()
    data class EmptyContent(val code: Any?):ScreenState()
    data class FilterSettings(val filters:FilterData,val btnAcceptVisibility:Boolean):ScreenState()
}