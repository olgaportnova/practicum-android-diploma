package ru.practicum.android.diploma.filter.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import ru.practicum.android.diploma.DefaultFragment
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentWorkPlaceBinding
import ru.practicum.android.diploma.filter.presentation.view_model.WorkPlaceVm

class WorkPlace : DefaultFragment<FragmentWorkPlaceBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentWorkPlaceBinding =
        FragmentWorkPlaceBinding::inflate

    lateinit var vm: WorkPlaceVm

    override fun setUiListeners() {
        with(binding) {
            navigationBar.setNavigationOnClickListener {
                exitExtraWhenSystemBackPushed()
            }

            btnChooseCountry.setOnClickListener {
                findNavController().navigate(R.id.action_to_country)
            }

            btnChooseDistrict.setOnClickListener {
                findNavController().navigate(
                    R.id.action_to_district,
                    Bundle().apply { putInt(ARG_COUNTRY_ID, 555) })
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
            val areaId = bundle.getInt(DISTRICT_ID)
            val areaName = bundle.getString(DISTRICT_NAME)

            // TODO: Изменять данные надо внутри viewModel
            vm.districtChosen.value?.let {
                vm.chooseAnotherDistrict(
                    it.copy(
                        name = areaName.toString(),
                        id = areaId
                    )
                )
            }
        }
        setFragmentResultListener(KEY_COUNTRY_RESULT) { requestKey, bundle ->
            val areaId = bundle.getInt(COUNTRY_ID)
            val areaName = bundle.getString(COUNTRY_NAME)

            // TODO: Изменять данные надо внутри viewModel
            vm.countryChosen.value?.let {
                vm.chooseAnotherCountry(
                    it.copy(
                        name = areaName.toString(),
                        id = areaId
                    )
                )
            }
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
    }

}