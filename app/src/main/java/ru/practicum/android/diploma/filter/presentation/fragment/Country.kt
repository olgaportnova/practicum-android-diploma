package ru.practicum.android.diploma.filter.presentation.fragment

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.filter.presentation.sharedviewmodel.FilterSharedVm
import ru.practicum.android.diploma.filter.presentation.util.ParentDataFragment
import ru.practicum.android.diploma.filter.presentation.view_model.CountryVm

class Country : ParentDataFragment() {
    override val vm: CountryVm by viewModel()
    private val sharedFiltersVm: FilterSharedVm by activityViewModels()

    override fun exitExtraWhenSystemBackPushed() {
        val filterSet = sharedFiltersVm.getFilters()
        val newCountry = vm.dataToSendBack

        filterSet?.let {
            val filter = when (newCountry) {
                null -> it.copy(idCountry = null, nameCountry = null)
                else -> it.copy(idCountry = newCountry.id.toString(), nameCountry = newCountry.name)
            }

            // Передаем изменения в sharedFiltersVm
            sharedFiltersVm.setFilter(remoteFilter = filter)
        }

        findNavController().popBackStack()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.navigationBar.title = getString(R.string.country_fragment_title)
        binding.txtSearch.isVisible = false
    }

}