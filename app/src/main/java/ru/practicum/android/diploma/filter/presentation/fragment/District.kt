package ru.practicum.android.diploma.filter.presentation.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.filter.presentation.sharedviewmodel.FilterSharedVm
import ru.practicum.android.diploma.filter.presentation.util.ARG_COUNTRY_ID
import ru.practicum.android.diploma.filter.presentation.util.ParentDataFragment
import ru.practicum.android.diploma.filter.presentation.view_model.DistrictVm

open class District : ParentDataFragment() {
    override val vm: DistrictVm by viewModel()
    private val sharedFiltersVm: FilterSharedVm by activityViewModels()

    override fun exitExtraWhenSystemBackPushed() {
        val filterSet = sharedFiltersVm.getFilters()
        val newArea = vm.dataToSendBack

        newArea?.let {
            //Log.e("LOG", "parent = ${it.parentId}")
            //vm.getParentName(it.parentId)
        }


        filterSet?.let {
            val filter = when (newArea) {
                null -> it.copy(idArea = null, nameArea = null)
                else -> {
                    if (paramCountryId != null) it.copy(
                        idArea = newArea.id.toString(),
                        nameArea = newArea.name
                    )
                    else {
                        it.copy(
                            idArea = newArea.id.toString(),
                            nameArea = newArea.name,
                            idCountry = newArea.parentId.toString(),
                            nameCountry = vm.getParentName(newArea.parentId)
                        )
                    }
                }
            }


            // Передаем изменения в sharedFiltersVm
            sharedFiltersVm.setFilter(remoteFilter = filter)
        }
        findNavController().popBackStack()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Распаковка переданного аргумента
        // В аргументе хранится id региона по которому, будет вестись поиск внутренних регионов

        arguments?.let {
            paramCountryId = it.getInt(ARG_COUNTRY_ID)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (paramCountryId != null) {
            paramCountryId?.let { id -> vm.loadDistrictList(id) }
        } else {
            Log.e("LOG", "district fragment No parent")
            vm.loadAreaTree()
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