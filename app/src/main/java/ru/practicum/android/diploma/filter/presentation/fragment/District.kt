package ru.practicum.android.diploma.filter.presentation.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.filter.presentation.util.ARG_COUNTRY_ID
import ru.practicum.android.diploma.filter.presentation.util.KEY_DISTRICT_RESULT
import ru.practicum.android.diploma.filter.presentation.util.ParentDataFragment
import ru.practicum.android.diploma.filter.presentation.view_model.DistrictVm
import ru.practicum.android.diploma.filter.recycler.AreaAdapter

open class District : ParentDataFragment() {
    override val vm: DistrictVm by viewModel()

    override fun exitExtraWhenSystemBackPushed() {
        // Для поиска вакансии по региону необходимо передать в поисковый запрос id региона
        setFragmentResult(KEY_DISTRICT_RESULT, getBackSendData())
        findNavController().popBackStack()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            paramCountryId = it.getInt(ARG_COUNTRY_ID)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (paramCountryId != null) {
            // Загрузка списка регионов производится только при наличии ненулевого id страны
            paramCountryId?.let { id -> vm.loadDistrictList(id) }
        }

        adapter.setNewItemClickListener() {
            vm.dataToSendBack = it
            vm.selectItemInDataList(it)
        }
    }

    companion object {
        fun newInstance(countryId: Int): District {
            return District().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COUNTRY_ID, countryId)
                }
            }
        }
    }
}