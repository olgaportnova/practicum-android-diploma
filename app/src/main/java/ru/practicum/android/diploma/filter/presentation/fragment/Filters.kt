package ru.practicum.android.diploma.filter.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFiltersBinding
import ru.practicum.android.diploma.filter.presentation.util.INDUSTRY_ID
import ru.practicum.android.diploma.filter.presentation.util.INDUSTRY_NAME
import ru.practicum.android.diploma.filter.presentation.util.KEY_INDUSTRY_RESULT
import ru.practicum.android.diploma.filter.presentation.util.ScreenState
import ru.practicum.android.diploma.filter.presentation.view_model.FiltersVm
import ru.practicum.android.diploma.util.DefaultFragment

class Filters : DefaultFragment<FragmentFiltersBinding>() {
    private val vm: FiltersVm by viewModel()
    override fun bindingInflater(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentFiltersBinding {
        return FragmentFiltersBinding.inflate(inflater, container, false)
    }

    override fun onResume() {
        super.onResume()
        vm.loadFilterSet()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFragmentResultListener(KEY_INDUSTRY_RESULT) { requestKey, bundle ->
            val industryName = bundle.getString(INDUSTRY_NAME)
            val industryId = bundle.getInt(INDUSTRY_ID)

            showMsgDialog(industryName.toString())
        }
    }

    override fun setUiListeners() {
        with(binding) {
            navigationBar.setNavigationOnClickListener {
                //parentFragmentManager.setFragmentResult("key", Bundle().apply { putString("bundleKey","Возврат с фрагмента Фильтры. Можно выполнить любую функцию") })
                exitExtraWhenSystemBackPushed()
            }

            btnChooseCountry.setOnClickListener {
                findNavController().navigate(R.id.action_to_workPlace)
            }

            btnChooseIndustry.setOnClickListener {
                findNavController().navigate(R.id.action_filters_to_industry,
                    Bundle().apply {

                    })
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
                vm.screenState.collect { bindFragmentState(it) }
            }
        }
    }

    private fun bindFragmentState(screenState: ScreenState) {

    }
}