package ru.practicum.android.diploma.filter.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController

const val KEY_COUNTRY_RESULT = "country_result"
const val COUNTRY_DATA = "country_id_param"
class Country : District() {
    override fun exitExtraWhenSystemBackPushed() {
        // Для поиска региона необходимо название страны или ее id
        // При выходе с фрагмента передаем родительскому фрагменту параметры выбранной страны
        val county = Bundle().apply {
            putParcelable(COUNTRY_DATA,vm.areaToSendBack)
        }
        setFragmentResult(KEY_COUNTRY_RESULT,county)
        findNavController().popBackStack()
    }
}