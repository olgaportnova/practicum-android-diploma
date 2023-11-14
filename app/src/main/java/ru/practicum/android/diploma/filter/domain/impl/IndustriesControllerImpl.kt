package ru.practicum.android.diploma.filter.domain.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.filter.domain.interfaces.IndustriesController
import ru.practicum.android.diploma.filter.domain.interfaces.IndustryRepository
import ru.practicum.android.diploma.filter.domain.models.CategoryData
import ru.practicum.android.diploma.util.DataStatus

class IndustriesControllerImpl(private val industryRepo: IndustryRepository) :
    IndustriesController {
    override suspend fun getIndustries(): Flow<DataStatus<List<CategoryData>>> =
        industryRepo.getIndustries()
}