package ru.practicum.android.diploma.hhApi

const val DEFAULT_NETWORK_RESPONSE = -111

sealed class DataStatus<out T>(val code: Int = DEFAULT_NETWORK_RESPONSE, val data: T? = null) {
    class Loading() : DataStatus<Nothing>()
    class Content<T>(networkData: T) : DataStatus<T>(data = networkData)
    class EmptyContent : DataStatus<Nothing>()
    class Error(newResponseCode: Int) : DataStatus<Nothing>(code = newResponseCode)
    class ErrorMessage(errorMessage: String) : DataStatus<Nothing>()

    //Дынный класс удобно размесить в domain слое и использовать дженерик для передачи
    //туда обертки класса Response, но его еще нет его нужно написать. Это то что обрабатывет ошибки
    //как вот этот класс AreaRepositoryImpl примерно.
}