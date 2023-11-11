package ru.practicum.android.diploma.filter.presentation.sharedviewmodel

import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.filter.domain.models.AbstractData
import ru.practicum.android.diploma.filter.domain.models.FilterData

class FilterSharedVm : ViewModel() {
    private var filters: FilterData? = null

    fun setFilter(remoteFilter: FilterData) {
        if(filters==null) filters = remoteFilter
    }

    fun setCountry(country: AbstractData) {
        filters?.let {
            filters = it.copy(idCountry = country.id.toString(), nameCountry = country.name)
        }

    }

    fun setDistrict(district: AbstractData) {
        filters?.let {
            filters = it.copy(idArea = district.id.toString(), nameArea = district.name)
        }
    }

    fun setIndustry(industry: AbstractData) {
        filters?.let {
            filters = it.copy(idIndustry = industry.id.toString(), nameIndustry = industry.name)
        }
    }

    fun getFilters() = this.filters


}