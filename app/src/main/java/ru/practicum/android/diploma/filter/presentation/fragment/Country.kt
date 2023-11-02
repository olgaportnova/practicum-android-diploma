package ru.practicum.android.diploma.filter.presentation.fragment

import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController

class Country : District() {
    override fun exitExtraWhenSystemBackPushed() {
        // Для поиска региона необходимо название страны или ее id
        // При выходе с фрагмента передаем родительскому фрагменту параметры выбранной страны
        vm.getAreaBundle()?.let { setFragmentResult(KEY_COUNTRY_RESULT, it) }
        findNavController().popBackStack()
    }
}