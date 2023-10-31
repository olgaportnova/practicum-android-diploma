package ru.practicum.android.diploma.filter.presentation.fragment

import android.os.Bundle
import android.view.View
import android.widget.Toast

class Country : District() {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Toast.makeText(requireContext(),"Выбор страны",Toast.LENGTH_LONG).show()
    }
}