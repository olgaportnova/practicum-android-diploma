package ru.practicum.android.diploma.filter.presentation.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.filter.presentation.util.INDUSTRY_ID
import ru.practicum.android.diploma.filter.presentation.util.INDUSTRY_NAME
import ru.practicum.android.diploma.filter.presentation.util.KEY_INDUSTRY_RESULT
import ru.practicum.android.diploma.filter.presentation.util.ParentDataFragment
import ru.practicum.android.diploma.filter.presentation.view_model.IndustryVm
import ru.practicum.android.diploma.filter.recycler.AreaAdapter

class Industry : ParentDataFragment() {
    override val vm: IndustryVm by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.navigationBar.title = "Индустрия"
        adapter.setAdapterType(AreaAdapter.CATEGORIES)
        adapter.setNewItemClickListener() {
            vm.dataToSendBack = it
            vm.selectItemInDataList(it) }
    }

    override fun exitExtraWhenSystemBackPushed() {
        // Для поиска вакансии по региону необходимо передать в поисковый запрос id региона
        setFragmentResult(KEY_INDUSTRY_RESULT, getBackSendData())
        findNavController().popBackStack()
    }

    override fun getBackSendData(): Bundle {
        return Bundle().apply {
            vm.dataToSendBack?.let {
                putInt(INDUSTRY_ID, it.id)
                putString(INDUSTRY_NAME, it.name)
            }
        }
    }
}