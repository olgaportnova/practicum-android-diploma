package ru.practicum.android.diploma.hhApi.dto

open class ResponseWrapper<T>(val code: Int = 0, val data: T? = null)