package ru.practicum.android.diploma.util

const val DEFAULT_NETWORK_RESPONSE = 0

sealed class DataStatus<out T>(val code: Int = DEFAULT_NETWORK_RESPONSE, val data: T? = null) {
    class Loading: DataStatus<Nothing>()
    class Content<T>(networkData:T) : DataStatus<T>(data = networkData)
    class EmptyContent: DataStatus<Nothing>()
    class Error(newResponseCode:Int = DEFAULT_NETWORK_RESPONSE,errorMessage:String = "Error") : DataStatus<Nothing>(code = newResponseCode)
    class NoConnecting(): DataStatus<Nothing>()
    class Default: DataStatus<Nothing>()
}