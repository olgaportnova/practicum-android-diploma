package ru.practicum.android.diploma.search.presentation.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import org.koin.androidx.viewmodel.ext.android.viewModel
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentSearchBinding
import ru.practicum.android.diploma.favorite.recycle_view.TopSpaceItemDecoration
import ru.practicum.android.diploma.favorite.recycle_view.VacancyAdapter
import ru.practicum.android.diploma.filter.domain.models.FilterData
import ru.practicum.android.diploma.search.domain.models.AnswerVacancyList
import ru.practicum.android.diploma.search.domain.models.QuerySearchMdl
import ru.practicum.android.diploma.search.domain.models.Vacancy
import ru.practicum.android.diploma.search.presentation.states.StateFilters
import ru.practicum.android.diploma.search.presentation.states.ToastState
import ru.practicum.android.diploma.search.presentation.view_model.SearchViewModel
import ru.practicum.android.diploma.util.DataStatus

class Search : Fragment() {

    companion object {
        const val APP_ERROR_SEARCH = 0
        const val PER_PAGE = 20
        const val START_PAGE = 0
        const val INIT_TEXT = ""
        const val ONE_PAGE = 1
    }

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SearchViewModel by viewModel()

    //private val _paramsFilter: SavedFilters? = null
    //private val paramsFilter get() = _paramsFilter!!
    private var _adapter: VacancyAdapter? = null
    private val adapter get() = _adapter

    //private val savedFilter: SavedFilters = SavedFilters()
    private var modelForQuery: QuerySearchMdl = QuerySearchMdl(
        page = START_PAGE, perPage = PER_PAGE, text = INIT_TEXT
    )

    private var _maxPage: Int? = null
    private val maxPage get() = _maxPage
    private var isSearchRequest = false
    private var currentPage: Int = START_PAGE

    private fun setUiListeners() {

        binding.navigationBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.nav_to_filter_fragment -> {
                    findNavController().navigate(R.id.action_to_filters)
                    true
                }

                else -> false
            }
        }
        binding.editTextSearch.addTextChangedListener(getTextWatcherForSearch())

        binding.recycleViewSearchResult.addOnScrollListener(onScrollListener())

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
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.stateToast.collect {
                    renderToast(it)
                    viewModel.setToastNoMessage()
                }
            }
        }

        initRecycler()
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
    private fun openFragmentVacancy(vacancyToShow: Int) {
        findNavController().navigate(
            R.id.action_search_to_vacancy,
            Bundle().apply { putInt("vacancy_model", vacancyToShow) })
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

    private fun renderToast(state: ToastState) {
        when (state) {
            is ToastState.ShowMessage -> showToast(state.message)
            is ToastState.NoneMessage -> {}
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
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
            editTextSearch.setText(INIT_TEXT)
            hideSoftKeyboard()
        }
    }

    private fun renderSearchErrorUi(codeError: Int) {
        if (modelForQuery.page == START_PAGE) {
            with(binding) {
                infoSearchResultCount.isVisible = false
                recycleViewSearchResult.isVisible = false
                progressBar.isVisible = false
                progressBarBottom.isVisible = false
                imagePlaceholder.setImageResource(R.drawable.placeholder_error_server)
                imagePlaceholder.isVisible = true
                textPlaceholder.setText(R.string.server_error)
                textPlaceholder.isVisible = true
            }
                setLogForError(codeError)
        } else {
            with(binding) {
                infoSearchResultCount.isVisible = true
                recycleViewSearchResult.isVisible = true
                progressBar.isVisible = false
                progressBarBottom.isVisible = false
                imagePlaceholder.isVisible = false
                textPlaceholder.isVisible = false
                setLogForError(codeError)
                viewModel.showToastDebounce(getString(R.string.error_for_toast))
            }
        }
    }

    private fun renderSearchLoadingUi() {
        if (modelForQuery.page == START_PAGE) {
            with(binding) {
                infoSearchResultCount.isVisible = false
                recycleViewSearchResult.isVisible = false
                progressBar.isVisible = true
                progressBarBottom.isVisible = false
                imagePlaceholder.isVisible = false
                textPlaceholder.isVisible = false
            }
        } else {
            with(binding) {
                infoSearchResultCount.isVisible = true
                recycleViewSearchResult.isVisible = true
                progressBar.isVisible = false
                progressBarBottom.isVisible = true
                imagePlaceholder.isVisible = false
                textPlaceholder.isVisible = false
            }
        }
    }

    private fun renderSearchNoConnectingUi() {
        if (modelForQuery.page == START_PAGE) {
            with(binding) {
                infoSearchResultCount.isVisible = false
                recycleViewSearchResult.isVisible = false
                progressBar.isVisible = false
                progressBarBottom.isVisible = false
                imagePlaceholder.setImageResource(R.drawable.placeholder_no_internet)
                imagePlaceholder.isVisible = true
                textPlaceholder.setText(R.string.no_internet)
                textPlaceholder.isVisible = true
            }
        } else {
            with(binding) {
                infoSearchResultCount.isVisible = true
                recycleViewSearchResult.isVisible = true
                progressBar.isVisible = false
                progressBarBottom.isVisible = false
                imagePlaceholder.isVisible = false
                textPlaceholder.isVisible = false
                viewModel.showToastDebounce(getString(R.string.no_internet_for_toast))
                isSearchRequest = true
            }
        }
    }

    private fun renderSearchEmptyUi() {
        with(binding) {
            infoSearchResultCount.setText(R.string.no_found_vacancies_count)
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

    @SuppressLint("SuspiciousIndentation")
    private fun renderSearchContentUi(data: AnswerVacancyList?) {
        with(binding) {
            infoSearchResultCount.text =
                requireContext().getString(R.string.found_vacancies_count, data!!.found)
            infoSearchResultCount.isVisible = true
            _maxPage = data.maxPages
            currentPage = data.currentPages
            if (data.currentPages == START_PAGE) {
                adapter!!.updateList(data.listVacancy, true, true)
            } else {
                adapter!!.updateList(data.listVacancy, true)
            }
            isSearchRequest = true

            progressBar.isVisible = false
            progressBarBottom.isVisible = false
            imagePlaceholder.isVisible = false
            textPlaceholder.isVisible = false
            recycleViewSearchResult.isVisible = true
        }
    }

    private fun renderNoFilters() {
        binding.navigationBar.menu.getItem(0).setIcon(R.drawable.ic_filters)
    }

    private fun renderUseFilters(content: FilterData) {
        binding.navigationBar.menu.getItem(0).setIcon(R.drawable.ic_filters_selected)
        addFilterInModel(content)

    }


    private fun addFilterInModel(content: FilterData) {
        with(modelForQuery) {
            page = START_PAGE
            perPage = PER_PAGE
            text = INIT_TEXT
            area = content.idArea
            parentArea = content.idCountry
            industry = content.idIndustry
            currency = content.currency
            salary = content.salary
            onlyWithSalary = content.onlyWithSalary
        }
    }

    private fun getTextWatcherForSearch(): TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //not use
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                hideIcDellText(p0)

                modelForQuery.text = p0.toString()
                modelForQuery.page = START_PAGE
                viewModel.searchDebounce(modelForQuery)
            }

            override fun afterTextChanged(p0: Editable?) {
                //not use
            }
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables", "ClickableViewAccessibility")
    private fun hideIcDellText(text: CharSequence?) {

        val editText = binding.editTextSearch

        if (text.isNullOrEmpty()) {
            binding.editTextSearch.setCompoundDrawablesWithIntrinsicBounds(
                0,
                0,
                R.drawable.ic_search,
                0
            )
            editText.setOnTouchListener { _, motionEvent ->
                false
            }
        } else {
            binding.editTextSearch.setCompoundDrawablesWithIntrinsicBounds(
                0,
                0,
                R.drawable.ic_clear,
                0
            )
            val iconClear = editText.compoundDrawables[2]
            editText.setOnTouchListener { _, motionEvent ->
                if ((motionEvent.action == MotionEvent.ACTION_UP) &&
                    (motionEvent.rawX >= (editText.right - iconClear.bounds.width() * 2))
                ) {
                    viewModel.setDefaultState()
                }
                true
            }
        }
    }

    private fun onScrollListener(): OnScrollListener {
        return object : OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {
                    val position =
                        (binding.recycleViewSearchResult.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                    val itemsCount = adapter!!.itemCount

                    if (position >= itemsCount - 1) {

                        if (isSearchRequest) {
                            isSearchRequest = false
                            if (maxPage!! >= currentPage + ONE_PAGE){
                                modelForQuery.page = currentPage + ONE_PAGE
                                viewModel.doRequestSearch(modelForQuery)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun initRecycler() {
        val spaceHeightInPixels =
            resources.getDimensionPixelSize(R.dimen.recycle_top_search_screen_margin)
        val itemDecoration = TopSpaceItemDecoration(spaceHeightInPixels)

        _adapter = VacancyAdapter(arrayListOf(), object : VacancyAdapter.OnClickListener {
            override fun onItemClick(vacancy: Vacancy) {
                openFragmentVacancy(vacancyToShow = vacancy.id)
            }
        })
        binding.recycleViewSearchResult.addItemDecoration(itemDecoration)

        binding.recycleViewSearchResult.adapter = adapter
        binding.recycleViewSearchResult.layoutManager = LinearLayoutManager(context)
    }

    private fun hideSoftKeyboard() {
        val inputMethodManager =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        inputMethodManager?.hideSoftInputFromWindow(binding.editTextSearch.windowToken, 0)
    }

    private fun setLogForError(codeError: Int) {
        if (codeError == APP_ERROR_SEARCH) {
            Log.e("AppErrorSearch", R.string.error_app_search_log.toString())
        } else {
            Log.e("ServerErrorSearch", "${R.string.error_sever_log} $codeError")
        }
    }
}