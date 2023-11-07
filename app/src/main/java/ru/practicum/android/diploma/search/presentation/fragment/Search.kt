package ru.practicum.android.diploma.search.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import androidx.navigation.fragment.findNavController
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentSearchBinding
import ru.practicum.android.diploma.search.domain.models.QuerySearchMdl
import ru.practicum.android.diploma.search.presentation.view_model.SearchViewModel

class Search : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SearchViewModel by viewModel()

    init {

    }

    private fun setUiListeners() {
//        //TODO: отработать нажание на item вакансии и переход
//        binding.txtTemporal.setOnClickListener {
//            openFragmentVacancy("vacancy from search")
//        }

        binding.navigationBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.nav_to_filter_fragment -> {
                    findNavController().navigate(R.id.action_to_filters)
                    true
                }
                else -> false
            }
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)

        // Inflate the layout for this fragment
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUiListeners()
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    /**
     * Function to open required vacancy details
     * @param vacancyToShow represent vacancy model
     * @author Oleg
     */
    // TODO: change param type
    private fun openFragmentVacancy(vacancyToShow: String) {
        findNavController().navigate(
            R.id.action_search_to_vacancy,
            Bundle().apply { putString("vacancy_model", vacancyToShow) })
    }


}