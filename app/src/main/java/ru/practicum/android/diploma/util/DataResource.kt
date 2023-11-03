package ru.practicum.android.diploma.util

sealed class DataResource<T>{
    class Data<T>(val data:T):DataResource<T>()
    class Empty<T>(val message:String):DataResource<T>()
}
