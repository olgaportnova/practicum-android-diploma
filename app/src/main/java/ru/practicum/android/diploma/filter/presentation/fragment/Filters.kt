package ru.practicum.android.diploma.filter.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFiltersBinding

class Filters : Fragment() {
    private var _binding: FragmentFiltersBinding? = null
    private val binding get() = _binding!!

    private fun setUiListeners() {
        with(binding) {
            navigationBar.setNavigationOnClickListener {
                //parentFragmentManager.setFragmentResult("key", Bundle().apply { putString("bundleKey","Возврат с фрагмента Фильтры. Можно выполнить любую функцию") })
                findNavController().popBackStack()
            }

            btnChooseCountry.setOnClickListener {
                findNavController().navigate(R.id.action_to_workPlace)
            }

            btnChooseIndustry.setOnClickListener {
                findNavController().navigate(R.id.action_filters_to_industry,
                    Bundle().apply {
                        putInt(ARG_FRAGMENT_TYPE, FragmentType.INDUSTRY.id)
                    })
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFiltersBinding.inflate(inflater, container, false)


        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUiListeners() // All listeners set
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}