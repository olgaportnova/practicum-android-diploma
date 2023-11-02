package ru.practicum.android.diploma.filter.presentation.fragment

import ru.practicum.android.diploma.filter.domain.models.Area

sealed class DistrictScreenState {
    data class Content(val data: List<Area>) : DistrictScreenState()
    data class Loading(val code: Any?) : DistrictScreenState()
    data class Error(val exception: String) : DistrictScreenState()
}