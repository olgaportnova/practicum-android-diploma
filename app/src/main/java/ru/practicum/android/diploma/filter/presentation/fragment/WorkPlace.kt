package ru.practicum.android.diploma.filter.presentation.fragment

import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentWorkPlaceBinding
import ru.practicum.android.diploma.filter.domain.models.AbstractData
import ru.practicum.android.diploma.filter.domain.models.AreaData
import ru.practicum.android.diploma.filter.presentation.util.AREA_ID
import ru.practicum.android.diploma.filter.presentation.util.AREA_NAME
import ru.practicum.android.diploma.filter.presentation.util.ARG_COUNTRY_ID
import ru.practicum.android.diploma.filter.presentation.util.KEY_COUNTRY_RESULT
import ru.practicum.android.diploma.filter.presentation.util.KEY_DISTRICT_RESULT
import ru.practicum.android.diploma.filter.presentation.sharedviewmodel.FilterSharedVm
import ru.practicum.android.diploma.filter.presentation.view_model.WorkPlaceVm
import ru.practicum.android.diploma.util.DefaultFragment

class WorkPlace : DefaultFragment<FragmentWorkPlaceBinding>() {
    private val vm: WorkPlaceVm by viewModel()
    private val viewModel: FilterSharedVm by activityViewModels()
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
                val parentId = when (vm.countryChosen.value) {
                    null -> 113
                    else -> vm.countryChosen.value!!.id
                }

                findNavController().navigate(
                    R.id.action_to_district,
                    Bundle().apply { putInt(ARG_COUNTRY_ID, parentId) })
            }

            btnClrCountry.setOnClickListener {
                vm.chooseAnotherCountry(null)
            }

            btnClrDistrict.setOnClickListener {
                vm.chooseAnotherDistrict(null)
            }

            btnChooseAll.setOnClickListener {
                vm.countryChosen.value?.let {
                    viewModel.setCountry(AbstractData(id = it.id, name = it.name))
                }
                vm.districtChosen.value?.let {
                    viewModel.setDistrict(AbstractData(id = it.id, name = it.name))
                }
                findNavController().popBackStack()
            }
        }
    }

    override fun exitExtraWhenSystemBackPushed() {
        // Exit back no saving
        findNavController().popBackStack()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFragmentResultListener(KEY_DISTRICT_RESULT) { requestKey, bundle ->
            val areaName = bundle.getString(AREA_NAME)
            val areaId = bundle.getInt(AREA_ID)

            val result = saveNewArea(areaName, areaId, WorkPlaceVm.AREA_TYPE_AREA)
            if (!result) vm.chooseAnotherDistrict(null)// Стираем отображение страны
        }


        setFragmentResultListener(KEY_COUNTRY_RESULT) { requestKey, bundle ->
            val areaName = bundle.getString(AREA_NAME)
            val areaId = bundle.getInt(AREA_ID)

            val result = saveNewArea(areaName, areaId, WorkPlaceVm.AREA_TYPE_COUNTRY)
            if (!result) vm.chooseAnotherCountry(null) // Стираем отображение региона
        }
    }

    private fun saveNewArea(name: String?, id: Int, areaType: Int): Boolean {
        if (name.isNullOrEmpty()) return false
        else {
            val area = AreaData(
                id = id,
                name = name,
                parentId = null,
                areas = emptyList()
            )

            if (areaType == WorkPlaceVm.AREA_TYPE_COUNTRY) vm.chooseAnotherCountry(newCountry = area)
            else vm.chooseAnotherDistrict(newDistrict = area)
        }
        return true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vm.countryChosen.observe(viewLifecycleOwner) {
            binding.lblChooseCountry.isVisible = it != null

            if (it != null) setCountrySelectedState(it)
            else setDefaultCountryState()
        }

        vm.districtChosen.observe(viewLifecycleOwner) {
            binding.lblChooseDistrict.isVisible = it != null

            if (it != null) setDistrictSelectedState(it)
            else setDefaultDistrictState()
        }

        vm.errorMsg.observe(viewLifecycleOwner) { showMsgDialog(it) }

        vm.acceptChanges.observe(viewLifecycleOwner) { binding.btnChooseAll.isVisible = it }
    }

    private fun getColorOnPrimary(): Int {
        val textColor = TypedValue()
        requireContext().theme.resolveAttribute(android.R.attr.textColorPrimary, textColor, true)
        return resources.getColor(textColor.resourceId, requireContext().theme)
    }

    private fun getGreyColor() = resources.getColor(R.color.grey_dark, requireContext().theme)

    private fun setCountrySelectedState(area: AreaData) {
        binding.txtChooseCountry.text = area.name
        binding.btnClrCountry.setImageResource(R.drawable.ic_clear)
        binding.txtChooseCountry.setTextColor(getColorOnPrimary())
    }

    private fun setDefaultCountryState() {
        binding.txtChooseCountry.text = getString(R.string.work_place_country_title)
        binding.btnClrCountry.setImageResource(R.drawable.baseline_arrow_forward_24)
        binding.txtChooseCountry.setTextColor(getGreyColor())
    }

    private fun setDistrictSelectedState(area: AreaData) {
        binding.txtChooseDistrict.text = area.name
        binding.btnClrDistrict.setImageResource(R.drawable.ic_clear)
        binding.txtChooseDistrict.setTextColor(getColorOnPrimary())
    }

    private fun setDefaultDistrictState() {
        binding.txtChooseDistrict.text = getString(R.string.work_place_district_title)
        binding.btnClrDistrict.setImageResource(R.drawable.baseline_arrow_forward_24)
        binding.txtChooseDistrict.setTextColor(getGreyColor())
    }
}