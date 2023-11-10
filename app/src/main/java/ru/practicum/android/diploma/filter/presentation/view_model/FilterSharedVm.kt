package ru.practicum.android.diploma.filter.presentation.view_model

import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.filter.domain.models.AbstractData

class FilterSharedVm : ViewModel() {
    var countryArea: AbstractData? = null
    var districtArea: AbstractData? = null
    var industry: AbstractData? = null

}