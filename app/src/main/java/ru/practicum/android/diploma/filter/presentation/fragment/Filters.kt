package ru.practicum.android.diploma.filter.presentation.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFiltersBinding
import ru.practicum.android.diploma.filter.domain.models.FilterData
import ru.practicum.android.diploma.filter.presentation.sharedviewmodel.FilterSharedVm
import ru.practicum.android.diploma.filter.presentation.util.KEY_FILTERS_RESULT
import ru.practicum.android.diploma.filter.presentation.util.ScreenState
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

            checkboxWithSalary.setOnClickListener {
                Log.e("LOG", "Click")
                vm.setWithSalaryParam()
            }

            txtSalaryInput.doOnTextChanged { text, start, before, count ->
                vm.setNewSalaryToFilter(text)
            }

            btnAcceptFilterSet.setOnClickListener {
                vm.saveNewFilterSet()
                setFragmentResult(KEY_FILTERS_RESULT, bundleOf())
                exitExtraWhenSystemBackPushed()
            }

            btnDeclineFilterSet.setOnClickListener { vm.abortFilters() }
        }
    }

    override fun exitExtraWhenSystemBackPushed() {
        findNavController().popBackStack()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Загружаем набор настроек фильтрации в объединенную модель при первой загрузке фрагмента
        // Внутри объединенной модели так же есть проверка, что не было многократной перезаписи
        viewModel.setFilter(vm.getFilters())
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

        vm.hasFilterSetChanged.observe(viewLifecycleOwner) {
            binding.btnAcceptFilterSet.isVisible = it
            binding.btnDeclineFilterSet.isVisible = it
        }
    }

    override fun onResume() {
        super.onResume()

        // При возвращении на фрагмент собираем потенциально полученную информацию
        // от фрагментов выбора страны, региона, профессии
        viewModel.getFilters()?.let {
            vm.updateFiltersWithRemote(it)
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

        binding.checkboxWithSalary.isChecked = filterSet.onlyWithSalary
        Log.e("LOG", "is checked ${filterSet.onlyWithSalary}")

        binding.txtSalaryInput.setText(filterSet.salary.toString())
    }
}