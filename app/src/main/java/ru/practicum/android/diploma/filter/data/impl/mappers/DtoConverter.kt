package ru.practicum.android.diploma.filter.data.impl.mappers

interface DtoConverter<In, Out> {
    fun convertFromDto(modelApi: In): Out
}



