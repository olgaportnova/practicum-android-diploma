package ru.practicum.android.diploma.filter.presentation.fragment

import ru.practicum.android.diploma.filter.domain.models.AreaData

sealed class DistrictScreenState {
    data class Content(val data: List<AreaData>) : DistrictScreenState()
    data class Loading(val code: Any?) : DistrictScreenState()
    data class Error(val exception: String) : DistrictScreenState()
}