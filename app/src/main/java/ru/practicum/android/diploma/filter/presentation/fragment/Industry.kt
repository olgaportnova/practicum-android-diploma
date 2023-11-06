package ru.practicum.android.diploma.filter.presentation.fragment

import android.os.Bundle
import android.view.View
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.filter.presentation.util.ParentDataFragment
import ru.practicum.android.diploma.filter.presentation.view_model.IndustryVm

class Industry : ParentDataFragment() {
    override val vm: IndustryVm by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.navigationBar.title = "Индустрия"
    }


}