package ru.practicum.android.diploma.filter.domain.impl

import ru.practicum.android.diploma.filter.domain.interfaces.AreaController
import ru.practicum.android.diploma.filter.domain.interfaces.AreaRepository

class AreaControllerImpl(private val areaRepo: AreaRepository) : AreaController {
    override suspend fun loadCountries() = areaRepo.loadCountries()
}