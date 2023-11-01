package ru.practicum.android.diploma.filter.presentation.fragment

import ru.practicum.android.diploma.filter.domain.models.Country

sealed class DistrictScreenState {
    data class Success(val data: List<Country>): DistrictScreenState()
    data class Loading(val code:Any?): DistrictScreenState()
    data class Error(val exception: String): DistrictScreenState()
}