package ru.practicum.android.diploma.filter.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentWorkPlaceBinding
import ru.practicum.android.diploma.filter.domain.models.FilterData
import ru.practicum.android.diploma.filter.presentation.sharedviewmodel.FilterSharedVm
import ru.practicum.android.diploma.filter.presentation.util.ARG_COUNTRY_ID
import ru.practicum.android.diploma.filter.presentation.view_model.WorkPlaceVm
import ru.practicum.android.diploma.util.DefaultFragment

class WorkPlace : DefaultFragment<FragmentWorkPlaceBinding>() {
    private val vm: WorkPlaceVm by viewModel()
    private val sharedFiltersVm: FilterSharedVm by activityViewModels()
    override fun bindingInflater(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentWorkPlaceBinding {
        return FragmentWorkPlaceBinding.inflate(inflater, container, false)
    }

    override fun setUiListeners() {
        with(binding) {
            navigationBar.setNavigationOnClickListener {
                exitExtraWhenSystemBackPushed()
            }

            layoutCountry.setOnClickListener {
                findNavController().navigate(R.id.action_to_country)
            }

            layoutDistrict.setOnClickListener {
                val parentId: Int = vm.getParentAreaIdToSearch() ?: 113

                findNavController().navigate(
                    R.id.action_to_district,
                    Bundle().apply { putInt(ARG_COUNTRY_ID, parentId) })
            }

            btnClrCountry.setOnClickListener {
                vm.clearCountry()
            }

            btnClrDistrict.setOnClickListener {
                vm.clearDistrict()
            }

            btnChooseAll.setOnClickListener {
                sharedFiltersVm.setFilter(remoteFilter = vm.getUpdatedFilterSet())
                findNavController().popBackStack()
            }
        }
    }

    override fun exitExtraWhenSystemBackPushed() {
        // Exit back no saving
        findNavController().popBackStack()
    }

    override fun onResume() {
        super.onResume()

        // При возвращении на фрагмент собираем потенциально полученную информацию
        // от фрагментов выбора страны, региона, профессии
        sharedFiltersVm.getFilters()?.let {
            vm.loadFilterSetFromSharedModel(it)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vm.errorMsg.observe(viewLifecycleOwner) { showMsgDialog(it) }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.screenState.collect {
                    if (it is ScreenWorkPlaceState.Content) {
                        renderScreen(it.filterSet)
                        renderAcceptButton(it.btnAcceptVisibility)
                    }
                }
            }
        }

    }

    private fun renderScreen(filterSet: FilterData) {
        // Заполняем поле страны
        if (filterSet.nameCountry.isNullOrEmpty()) {
            setDefaultCountryState()
        } else {
            setCountrySelectedState(filterSet.nameCountry)
        }

        // Заполняем поле района
        if (filterSet.nameArea.isNullOrEmpty()) {
            setDefaultDistrictState()
        } else {
            setDistrictSelectedState(filterSet.nameArea)
        }
    }

    private fun renderAcceptButton(visibility: Boolean) {
        binding.btnChooseAll.isVisible = visibility
    }

    private fun setCountrySelectedState(name: String) {
        binding.txtChooseCountry.text = name
        binding.btnClrCountry.setImageResource(R.drawable.ic_clear_small)
        binding.lblChooseCountryInitialBig.visibility=View.GONE
        binding.txtChooseCountry.visibility=View.VISIBLE
        binding.lblChooseCountry.visibility=View.VISIBLE
    }

    private fun setDefaultCountryState() {
        binding.lblChooseCountryInitialBig.text = getString(R.string.work_place_country_title)
        binding.btnClrCountry.setImageResource(R.drawable.baseline_arrow)
        binding.lblChooseCountryInitialBig.visibility=View.VISIBLE
        binding.txtChooseCountry.visibility=View.GONE
        binding.lblChooseCountry.visibility=View.GONE
    }

    private fun setDistrictSelectedState(name: String) {
        binding.txtChooseDistrict.text = name
        binding.btnClrDistrict.setImageResource(R.drawable.ic_clear_small)
        binding.lblChooseDistrictInitialBig.visibility=View.GONE
        binding.txtChooseDistrict.visibility=View.VISIBLE
        binding.lblChooseDistrict.visibility=View.VISIBLE
    }

    private fun setDefaultDistrictState() {
        binding.lblChooseDistrictInitialBig.text = getString(R.string.work_place_district_title)
        binding.btnClrDistrict.setImageResource(R.drawable.baseline_arrow)
        binding.lblChooseDistrictInitialBig.visibility=View.VISIBLE
        binding.txtChooseDistrict.visibility=View.GONE
        binding.lblChooseDistrict.visibility=View.GONE
    }
}