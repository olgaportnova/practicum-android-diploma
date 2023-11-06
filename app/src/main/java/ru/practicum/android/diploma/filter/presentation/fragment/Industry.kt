package ru.practicum.android.diploma.filter.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.databinding.FragmentDistrictBinding
import ru.practicum.android.diploma.filter.presentation.view_model.IndustryVm
import ru.practicum.android.diploma.util.DefaultFragment

class Industry: DefaultFragment<FragmentDistrictBinding>() {
    private val vm:IndustryVm by viewModel()
    override fun bindingInflater(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentDistrictBinding =FragmentDistrictBinding.inflate(inflater,container,false)

    override fun setUiListeners() {
        binding.navigationBar.title="Industry"
    }

    override fun exitExtraWhenSystemBackPushed() {
        findNavController().popBackStack()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vm.errorMsg.observe(viewLifecycleOwner){
            showMsgDialog(it)
        }
    }



}