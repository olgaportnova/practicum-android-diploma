package ru.practicum.android.diploma.filter.domain.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.filter.domain.interfaces.AreaController
import ru.practicum.android.diploma.filter.domain.interfaces.AreaRepository
import ru.practicum.android.diploma.filter.domain.models.Area
import ru.practicum.android.diploma.filter.network.DataStatus

class AreaControllerImpl(private val areaRepo: AreaRepository) : AreaController {
    override suspend fun loadCountries() = areaRepo.loadCountries()
    override suspend fun loadDistricts(parentId: Int)=areaRepo.loadDistricts(parentId)
}