package ru.practicum.android.diploma.filter.presentation.util

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.databinding.FragmentDistrictBinding
import ru.practicum.android.diploma.filter.recycler.AreaAdapter
import ru.practicum.android.diploma.util.DefaultFragment

const val ARG_COUNTRY_ID = "country_id_pram"

const val KEY_DISTRICT_RESULT = "district_result"
const val KEY_COUNTRY_RESULT = "area_result"
const val KEY_INDUSTRY_RESULT = "industry_result"

const val AREA_ID = "area_id_param"
const val AREA_NAME = "area_name_param"
const val INDUSTRY_ID = "industry_id_param"
const val INDUSTRY_NAME = "industry_name_param"

open class ParentDataFragment : DefaultFragment<FragmentDistrictBinding>() {
    protected var paramCountryId: Int? = null // Считывается из аргументов в onCreate
    open val vm: DefaultViewModel? = null

    private val adapter = AreaAdapter(mutableListOf()) {
        vm?.dataToSendBack = it
        exitExtraWhenSystemBackPushed() // Exit after choosing required area
    }

    override fun bindingInflater(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentDistrictBinding {
        return FragmentDistrictBinding.inflate(inflater, container, false)
    }

    override fun setUiListeners() {
        with(binding) {
            navigationBar.setNavigationOnClickListener {
                exitExtraWhenSystemBackPushed()
            }
        }
    }

    open fun setObservers() {
        vm?.let {
            with(it) {
                errorMsg.observe(viewLifecycleOwner) { msg -> showMsgDialog(msg) }
            }
        }
    }

    open fun observeFlowScreenState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm?.screenState?.collect {
                    setFragmentScreenState(it)
                }
            }
        }
    }

    override fun exitExtraWhenSystemBackPushed() {
        findNavController().popBackStack()
    }

    private fun setUpAdapter() {
        binding.areaRecycler.adapter = adapter
        binding.areaRecycler.layoutManager = LinearLayoutManager(requireContext())
    }

    open fun getBackSendData(): Bundle {
        return Bundle().apply {
            vm?.dataToSendBack?.let { data ->
                putInt(AREA_ID, data.id)
                putString(AREA_NAME, data.name)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpAdapter()
        setUiListeners()
        setObservers()
        observeFlowScreenState()
    }

    private fun setFragmentScreenState(newScreenState: ScreenState) {
        when (newScreenState) {
            is ScreenState.Loading -> binding.txtContent.text = "Initial "
            is ScreenState.Content -> adapter.changeData(newScreenState.data)
            is ScreenState.Error -> showMsgDialog(newScreenState.exception)
        }
    }


}