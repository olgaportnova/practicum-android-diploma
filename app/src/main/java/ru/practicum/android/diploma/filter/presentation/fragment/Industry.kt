package ru.practicum.android.diploma.filter.presentation.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.filter.domain.models.AbstractData
import ru.practicum.android.diploma.filter.presentation.util.ParentDataFragment
import ru.practicum.android.diploma.filter.presentation.sharedviewmodel.FilterSharedVm
import ru.practicum.android.diploma.filter.presentation.view_model.IndustryVm
import ru.practicum.android.diploma.filter.recycler.AreaAdapter

class Industry : ParentDataFragment() {
    override val vm: IndustryVm by viewModel()
    private val sharedFilterViewModel: FilterSharedVm by activityViewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Если в настройках фильтрации есть выбранная профессия, записываем в переменную
        vm.setPreselectedIndustryId(sharedFilterViewModel.getFilters()?.idIndustry?.toIntOrNull())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.navigationBar.setTitle(R.string.filters_choose_industry)
        binding.txtSearch.setHint(R.string.hint_for_edit_text_industry)
        binding.textPlaceholderEmptyList.setText(R.string.no_such_industry)
        adapter.setAdapterType(AreaAdapter.CATEGORIES) // Поменяли тип recycler

        // Переопределяем поведение при нажатии на элемент recycler
        adapter.setNewItemClickListener() {
            vm.dataToSendBack = it // Сохраняем данные выбранного элемента
            vm.selectItemInDataList(it) // Выделяем элемент в recycler
        }
    }

    override fun exitExtraWhenSystemBackPushed() {
        // Для поиска вакансии по региону необходимо передать в поисковый запрос id региона
        vm.dataToSendBack?.let {
            sharedFilterViewModel.setIndustry(AbstractData(id = it.id, name = it.name))
        }
        findNavController().popBackStack()
    }
}