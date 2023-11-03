package ru.practicum.android.diploma.filter.network

const val DEFAULT_NETWORK_RESPONSE = -111

sealed class DataStatus<out T>(val code: Int = DEFAULT_NETWORK_RESPONSE, val data: T? = null) {
    class Loading() : DataStatus<Nothing>()
    class Content<T>(networkData: T) : DataStatus<T>(data = networkData)
    class EmptyContent : DataStatus<Nothing>()
    class Error(newResponseCode: Int) : DataStatus<Nothing>(code = newResponseCode)
    class ErrorMessage(errorMessage: String) : DataStatus<Nothing>()
}