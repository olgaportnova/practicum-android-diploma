package ru.practicum.android.diploma.filter.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import ru.practicum.android.diploma.DefaultFragment
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
                // Функция вызванная с нулевыми параметрами обнуляет поле _countryChosen in viewModel
                vm.chooseAnotherCountry(null, null)
            }

            btnClrDistrict.setOnClickListener {
                // Функция вызванная с нулевыми параметрами обнуляет поле _districtChosen in viewModel
                vm.chooseAnotherDistrict(null, null)
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

        //binding.btnChooseAll.setBackgroundColor(requireActivity().getColor(R.color.blue_main))

        vm.countryChosen.observe(viewLifecycleOwner) {
            binding.lblChooseCountry.isVisible = it != null

            if (it != null) {
                // Если выбрана страна, устанавливаем заголовок и ставим иконку "стереть"
                binding.txtChooseCountry.text = it.name
                binding.btnClrCountry.setImageResource(R.drawable.ic_clear)

                // Так же устанавливаем цвет текста
                binding.txtChooseCountry.setTextColor(
                    resources.getColor(
                        R.color.blue_main,
                        requireContext().theme
                    )
                )
            } else {
                binding.txtChooseCountry.text = getString(R.string.work_place_country_title)
                binding.btnClrCountry.setImageResource(R.drawable.baseline_arrow_forward_24)

                // Так же устанавливаем цвет текста
                binding.txtChooseCountry.setTextColor(
                    resources.getColor(
                        R.color.grey_dark,
                        requireContext().theme
                    )
                )
            }
        }

        vm.districtChosen.observe(viewLifecycleOwner) {
            binding.lblChooseDistrict.isVisible = it != null

            if (it != null) {
                // Если выбран район, устанавливаем заголовок и ставим иконку "стереть"
                binding.txtChooseDistrict.text = it.name
                binding.btnClrDistrict.setImageResource(R.drawable.ic_clear)

                // Так же устанавливаем цвет текста
                binding.txtChooseDistrict.setTextColor(
                    resources.getColor(
                        R.color.blue_main,
                        requireContext().theme
                    )
                )
            } else {
                // Если район не выбран или стерт удаляем заголовок и ставим иконку "forward"
                binding.txtChooseDistrict.text = getString(R.string.work_place_district_title)
                binding.btnClrDistrict.setImageResource(R.drawable.baseline_arrow_forward_24)

                // Так же устанавливаем цвет текста
                binding.txtChooseDistrict.setTextColor(
                    resources.getColor(
                        R.color.grey_dark,
                        requireContext().theme
                    )
                )
            }

        }

        vm.errorMsg.observe(viewLifecycleOwner) {
            showMsgDialog(it)
        }

        vm.acceptChanges.observe(viewLifecycleOwner) {
            binding.btnChooseAll.isVisible = it
        }
    }

}