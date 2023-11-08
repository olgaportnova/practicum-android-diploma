package ru.practicum.android.diploma.search.presentation.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import org.koin.androidx.viewmodel.ext.android.viewModel
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentSearchBinding
import ru.practicum.android.diploma.favorite.recycle_view.VacancyAdapter
import ru.practicum.android.diploma.filter.domain.models.FilterData
import ru.practicum.android.diploma.search.domain.models.AnswerVacancyList
import ru.practicum.android.diploma.search.presentation.SavedFilters
import ru.practicum.android.diploma.search.presentation.states.StateFilters
import ru.practicum.android.diploma.search.presentation.view_model.SearchViewModel
import ru.practicum.android.diploma.util.DataStatus

class Search : Fragment() {

    companion object {
        const val APP_ERROR_SEARCH = 0
        const val PER_PAGE = 20
        const val START_PAGE = 0
    }

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SearchViewModel by viewModel()

    private val _paramsFilter: SavedFilters? = null
    private val paramsFilter get() = _paramsFilter!!
    private val _adapter: VacancyAdapter? = null
    private val adapter get() = _adapter

    private val currentPage = START_PAGE

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


    @SuppressLint("UnsafeRepeatOnLifecycleDetector")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUiListeners()

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.stateFilters.collect {
                    renderFiltersUi(it)
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.stateSearch.collect {
                    renderSearchUi(it)
                }
            }
        }

        viewModel.getParamsFilters()
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

    private fun renderFiltersUi(state: StateFilters) {
        when (state) {
            is StateFilters.NoUseFilters -> {
                renderNoFilters()
            }

            is StateFilters.UseFilters -> {
                renderUseFilters(state.content)
            }
        }
    }

    private fun renderSearchUi(state: DataStatus<AnswerVacancyList>) {
        when (state) {
            is DataStatus.Default -> {
                renderSearchDefaultUi()
            }

            is DataStatus.Error -> {
                renderSearchErrorUi(state.code)
            }

            is DataStatus.Loading -> {
                renderSearchLoadingUi()
            }

            is DataStatus.NoConnecting -> {
                renderSearchNoConnectingUi()
            }

            is DataStatus.Content -> {
                renderSearchContentUi(state.data)
            }

            is DataStatus.EmptyContent -> {
                renderSearchEmptyUi()
            }
        }
    }

    private fun renderSearchDefaultUi() {
        with(binding) {
            infoSearchResultCount.isVisible = false
            recycleViewSearchResult.isVisible = false
            progressBar.isVisible = false
            progressBarBottom.isVisible = false
            imagePlaceholder.setImageResource(R.drawable.placeholder_start_of_search)
            imagePlaceholder.isVisible = true
            textPlaceholder.isVisible = false
        }
    }

    private fun renderSearchErrorUi(codeError: Int) {
        with(binding) {
            infoSearchResultCount.isVisible = false
            recycleViewSearchResult.isVisible = false
            progressBar.isVisible = false
            progressBarBottom.isVisible = false
            imagePlaceholder.setImageResource(R.drawable.placeholder_error_server)
            imagePlaceholder.isVisible = true
            textPlaceholder.setText(R.string.server_error)
        }
        if (codeError == APP_ERROR_SEARCH) {
            Log.e("AppErrorSearch", R.string.error_app_search_log.toString())
        } else {
            Log.e("ServerErrorSearch", "${R.string.error_sever_log} $codeError")
        }
    }

    private fun renderSearchLoadingUi(){
     if(currentPage == START_PAGE){
        with(binding){
            infoSearchResultCount.isVisible = false
            recycleViewSearchResult.isVisible = false
            progressBar.isVisible= true
            progressBarBottom.isVisible = false
            imagePlaceholder.isVisible = false
            textPlaceholder.isVisible= false
        }
     }
        else{
         with(binding){
             infoSearchResultCount.isVisible = true
             recycleViewSearchResult.isVisible = true
             progressBar.isVisible = false
             progressBarBottom.isVisible = true
             imagePlaceholder.isVisible = false
             textPlaceholder.isVisible = false
         }
        }
    }

    private fun renderSearchNoConnectingUi(){
        with(binding){
            infoSearchResultCount.isVisible = false
            recycleViewSearchResult.isVisible = false
            progressBar.isVisible = false
            progressBarBottom.isVisible = false
            imagePlaceholder.setImageResource(R.drawable.placeholder_no_internet)
            imagePlaceholder.isVisible = true
            textPlaceholder.setText(R.string.no_internet)
            textPlaceholder.isVisible = true
        }
    }

    private fun renderSearchEmptyUi(){
        with(binding){
            infoSearchResultCount.isVisible = true
            recycleViewSearchResult.isVisible = false
            progressBar.isVisible = false
            progressBarBottom.isVisible = false
            imagePlaceholder.setImageResource(R.drawable.placeholder_empty_result)
            imagePlaceholder.isVisible = true
            textPlaceholder.setText(R.string.no_search_result)
            textPlaceholder.isVisible = true
        }
    }

    private fun renderSearchContentUi(data: AnswerVacancyList?) {
        binding.recycleViewSearchResult.isVisible = true

    }

    private fun renderNoFilters() {
        binding.navigationBar.setNavigationIcon(R.drawable.ic_filters)
    }

    private fun renderUseFilters(content: FilterData) {
        binding.navigationBar.setNavigationIcon(R.drawable.ic_filters_selected)
    //TODO:Logic get SH data
    }
}