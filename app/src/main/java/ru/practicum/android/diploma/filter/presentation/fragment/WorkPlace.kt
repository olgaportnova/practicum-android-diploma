package ru.practicum.android.diploma.filter.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import ru.practicum.android.diploma.util.DefaultFragment
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentWorkPlaceBinding
import ru.practicum.android.diploma.filter.presentation.view_model.WorkPlaceVm

class WorkPlace : DefaultFragment<FragmentWorkPlaceBinding>() {
    override fun bindingInflater(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentWorkPlaceBinding {
        return FragmentWorkPlaceBinding.inflate(inflater, container, false)
    }

    private lateinit var vm: WorkPlaceVm

    override fun setUiListeners() {
        with(binding) {
            navigationBar.setNavigationOnClickListener {
                exitExtraWhenSystemBackPushed()
            }

            btnChooseCountry.setOnClickListener {
                findNavController().navigate(R.id.action_to_country)
            }

            btnChooseDistrict.setOnClickListener {
                val parentId = when (vm.countryChosen.value) {
                    null -> 113
                    else -> vm.countryChosen.value!!.id
                }

                findNavController().navigate(
                    R.id.action_to_district,
                    Bundle().apply { putInt(ARG_COUNTRY_ID, parentId) })
            }
        }
    }

    override fun exitExtraWhenSystemBackPushed() {
        // TODO: Вызвать сохранение модели данных фильтра в sharedPrefs
        findNavController().popBackStack()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Потом сделать через Koin by viewModel()
        vm = ViewModelProvider(this)[WorkPlaceVm::class.java]


        setFragmentResultListener(KEY_DISTRICT_RESULT) { requestKey, bundle ->
            val areaName = bundle.getString(AREA_NAME)
            val areaId = bundle.getInt(AREA_ID)

            vm.chooseAnotherDistrict(areaId, areaName)
        }


        setFragmentResultListener(KEY_COUNTRY_RESULT) { requestKey, bundle ->
            val areaName = bundle.getString(AREA_NAME)
            val areaId = bundle.getInt(AREA_ID)

            vm.chooseAnotherCountry(areaId, areaName)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vm.countryChosen.observe(viewLifecycleOwner) {
            binding.btnChooseCountry.text = it.name
        }

        vm.districtChosen.observe(viewLifecycleOwner) {
            binding.btnChooseDistrict.text = it.name
        }

        vm.errorMsg.observe(viewLifecycleOwner) {
            showMsgDialog(it)
        }
    }

}