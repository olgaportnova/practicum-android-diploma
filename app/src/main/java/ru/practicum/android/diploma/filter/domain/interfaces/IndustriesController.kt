package ru.practicum.android.diploma.filter.domain.interfaces

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.filter.domain.models.CategoryData
import ru.practicum.android.diploma.util.DataStatus

interface IndustriesController {
    suspend fun getIndustries(): Flow<DataStatus<List<CategoryData>>>
}