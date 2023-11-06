package ru.practicum.android.diploma.filter.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFiltersBinding
import ru.practicum.android.diploma.filter.presentation.util.INDUSTRY_ID
import ru.practicum.android.diploma.filter.presentation.util.INDUSTRY_NAME
import ru.practicum.android.diploma.filter.presentation.util.KEY_INDUSTRY_RESULT
import ru.practicum.android.diploma.util.DefaultFragment

class Filters : DefaultFragment<FragmentFiltersBinding>() {
    override fun bindingInflater(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentFiltersBinding {
        return FragmentFiltersBinding.inflate(inflater, container, false)
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
}