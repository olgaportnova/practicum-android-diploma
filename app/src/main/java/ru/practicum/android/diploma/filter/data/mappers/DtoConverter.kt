package ru.practicum.android.diploma.filter.data.mappers

interface DtoConverter<In, Out> {
    fun convertFromDto(modelApi: In): Out
}