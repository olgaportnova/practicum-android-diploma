package ru.practicum.android.diploma.filter.presentation.fragment

import androidx.navigation.fragment.findNavController

class Industry:District() {
    override fun exitExtraWhenSystemBackPushed() {
        findNavController().popBackStack()
    }
}