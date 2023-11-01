package ru.practicum.android.diploma.filter.data.impl

import ru.practicum.android.diploma.filter.domain.interfaces.AreaRepository
import ru.practicum.android.diploma.util.DataResource

class AreaRepositoryImpl:AreaRepository {
    override fun loadCountries(): DataResource<String> {
        return DataResource.Data(data = "Temporal answer")
    }
}