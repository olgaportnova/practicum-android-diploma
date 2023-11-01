package ru.practicum.android.diploma.util

const val DEFAULT_NETWORK_RESPONSE = -111

class NetworkResponse(var networkData: Any? = null) {
    var responseCode = DEFAULT_NETWORK_RESPONSE
    var exceptionName:String = ""

    fun setResponseCode(newCode: Int): NetworkResponse {
        return this.apply { responseCode = newCode }
    }

    fun setNewData(newData: Any?): NetworkResponse {
        return this.apply { networkData = newData }
    }

    fun transferException(exception: Throwable): NetworkResponse {
        return this.apply { exceptionName = exception.message.toString() }
    }

}