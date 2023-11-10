package ru.practicum.android.diploma.search.presentation.states

sealed class ToastState{
    object NoneMessage: ToastState()
    class ShowMessage(val message: String):ToastState()
}
