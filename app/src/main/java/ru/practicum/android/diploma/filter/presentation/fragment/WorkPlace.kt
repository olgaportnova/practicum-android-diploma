package ru.practicum.android.diploma.filter.presentation.fragment

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import ru.practicum.android.diploma.DefaultFragment
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentWorkPlaceBinding
import ru.practicum.android.diploma.filter.domain.models.Area
import ru.practicum.android.diploma.filter.presentation.view_model.WorkPlaceVm

class WorkPlace : DefaultFragment<FragmentWorkPlaceBinding>() {
    override fun bindingInflater(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentWorkPlaceBinding {
        return FragmentWorkPlaceBinding.inflate(inflater, container, false)
    }

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

    // TODO: Разобраться с версией для правильной обработки getParcelable
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Потом сделать через Koin by viewModel()
        vm = ViewModelProvider(this)[WorkPlaceVm::class.java]

        setFragmentResultListener(KEY_DISTRICT_RESULT) { requestKey, bundle ->
            val area = bundle.getParcelable(DISTRICT_DATA, Area::class.java)
            area?.let { vm.chooseAnotherDistrict(area) }
        }

        setFragmentResultListener(KEY_COUNTRY_RESULT) { requestKey, bundle ->
            val area = bundle.getParcelable(DISTRICT_DATA, Area::class.java)
            area?.let { vm.chooseAnotherCountry(area) }
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