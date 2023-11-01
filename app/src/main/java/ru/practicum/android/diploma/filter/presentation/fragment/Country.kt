package ru.practicum.android.diploma.filter.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import kotlin.random.Random

const val KEY_COUNTRY_RESULT = "country_result"
const val COUNTRY_NAME = "country_name_param"
const val COUNTRY_ID = "country_id_param"
class Country : District() {

    override fun exitExtraWhenSystemBackPushed() {
        // Для поиска региона необходимо название страны или ее id
        // Параметр county - Регион. Необходимо передавать id из справочника /areas. Можно указать несколько значений
        val county = Bundle().apply {
            putInt(COUNTRY_ID, Random.nextInt(10000))
            putString(COUNTRY_NAME,"Зимбабве")
        }
        setFragmentResult(KEY_COUNTRY_RESULT,county)
        findNavController().popBackStack()
    }
}