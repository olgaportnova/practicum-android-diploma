package ru.practicum.android.diploma.filter.domain.impl

import ru.practicum.android.diploma.filter.domain.interfaces.AreaController
import ru.practicum.android.diploma.filter.domain.interfaces.AreaRepository

class AreaControllerImpl(private val areaRepo: AreaRepository) : AreaController {
    //Данные названия не менял на getAreas и getDistricts соответственно. Можно поменять.
    override suspend fun loadCountries() = areaRepo.loadCountries()
    override suspend fun loadDistricts(parentId: Int)=areaRepo.loadDistricts(parentId)
}