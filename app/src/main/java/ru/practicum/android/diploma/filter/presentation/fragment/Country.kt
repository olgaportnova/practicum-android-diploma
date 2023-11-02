package ru.practicum.android.diploma.filter.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController

const val KEY_COUNTRY_RESULT = "area_result"
const val AREA_DATA = "area_id_param"
class Country : District() {
    override fun exitExtraWhenSystemBackPushed() {
        // Для поиска региона необходимо название страны или ее id
        // При выходе с фрагмента передаем родительскому фрагменту параметры выбранной страны
        val area = Bundle().apply {
            putParcelable(AREA_DATA,vm.areaToSendBack)
        }
        setFragmentResult(KEY_COUNTRY_RESULT,area)
        findNavController().popBackStack()
    }
}