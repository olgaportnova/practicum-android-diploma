package ru.practicum.android.diploma.filter.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFiltersBinding
import ru.practicum.android.diploma.filter.domain.models.FilterData
import ru.practicum.android.diploma.filter.presentation.util.ScreenState
import ru.practicum.android.diploma.filter.presentation.view_model.FilterSharedVm
import ru.practicum.android.diploma.filter.presentation.view_model.FiltersVm
import ru.practicum.android.diploma.util.DefaultFragment

class Filters : DefaultFragment<FragmentFiltersBinding>() {
    private val vm: FiltersVm by viewModel()
    private val viewModel: FilterSharedVm by activityViewModels()
    override fun bindingInflater(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentFiltersBinding {
        return FragmentFiltersBinding.inflate(inflater, container, false)
    }


    override fun setUiListeners() {
        with(binding) {
            navigationBar.setNavigationOnClickListener {
                exitExtraWhenSystemBackPushed()
            }

            layoutWorkPlace.setOnClickListener {
                findNavController().navigate(R.id.action_to_workPlace)
            }

            layoutIndustry.setOnClickListener {
                findNavController().navigate(R.id.action_filters_to_industry,
                    Bundle().apply {

                    })
            }

            checkboxWithSalry.setOnClickListener {
                vm.setWithSalaryParam(it.isPressed)
            }

            txtSalaryInput.doOnTextChanged { text, start, before, count ->
                vm.setNewSalaryToFilter(text)
            }
        }
    }

    override fun exitExtraWhenSystemBackPushed() {
        findNavController().popBackStack()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.screenState.collect {
                    if (it is ScreenState.FilterSettings) bindFragmentState(it.filters)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()

        viewModel.countryArea?.let {
            vm.setNewCountryToFilter(it.name, it.id)
        }

        viewModel.districtArea?.let {
            vm.setNewAreToFilter(it.name, it.id)
        }

        viewModel.industry?.let {
            vm.setNewIndustryToFilter(it.name, it.id)
        }
    }

    private fun bindFragmentState(filterSet: FilterData) {
        if (filterSet.idCountry != null) {
            binding.lblChooseWorkPlace.isVisible = true
            binding.txtChooseWorkPlace.isVisible = true
            binding.txtChooseWorkPlace.text = filterSet.nameCountry
        } else {
            binding.txtChooseWorkPlace.isVisible = false
        }

        if (filterSet.idArea != null) {
            binding.txtChooseWorkPlace.text = "${filterSet.nameCountry}, ${filterSet.nameArea}"
        }

        if (filterSet.idIndustry != null) {

            binding.lblChooseIndustry.isVisible = true
            binding.txtChooseIndustry.isVisible = true
            binding.txtChooseIndustry.text = filterSet.nameIndustry
        } else {
            binding.txtChooseIndustry.isVisible = false
        }


    }
}