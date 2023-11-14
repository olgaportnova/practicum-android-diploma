package ru.practicum.android.diploma.search.presentation.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentSearchBinding
import ru.practicum.android.diploma.favorite.recycle_view.TopSpaceItemDecoration
import ru.practicum.android.diploma.favorite.recycle_view.VacancyAdapter
import ru.practicum.android.diploma.filter.domain.models.FilterData
import ru.practicum.android.diploma.filter.presentation.util.KEY_FILTERS_RESULT
import ru.practicum.android.diploma.search.domain.models.AnswerVacancyList
import ru.practicum.android.diploma.search.domain.models.QuerySearchMdl
import ru.practicum.android.diploma.search.domain.models.Vacancy
import ru.practicum.android.diploma.search.presentation.states.StateSearch
import ru.practicum.android.diploma.search.presentation.view_model.SearchViewModel
import ru.practicum.android.diploma.util.DefaultFragment

class Search : DefaultFragment<FragmentSearchBinding>() {

    companion object {
        const val TOAST_DEBOUNCE_DELAY_ML = 10000L
        const val PER_PAGE = 20
        const val START_PAGE_INDEX = 0
        const val INIT_TEXT = ""
        const val ONE_PAGE_INDEX = 1
    }

    private val viewModel: SearchViewModel by viewModel()

    private var _adapter: VacancyAdapter? = null
    private val adapter get() = _adapter
    private val listVacancy: ArrayList<Vacancy> = arrayListOf()
    private var tempListVacancy: List<Vacancy> = arrayListOf()

    private var modelForQuery: QuerySearchMdl = QuerySearchMdl(
        page = START_PAGE_INDEX, perPage = PER_PAGE, text = INIT_TEXT
    )
    private var _maxPage: Int? = null
    private val maxPage get() = _maxPage
    private var isSearchRequest = false
    private var isGetParamsFragment = false
    private var filterData: FilterData? = null
    private var currentPage: Int = START_PAGE_INDEX
    private var tempValueEditText: String = INIT_TEXT
    private var isShowToast = true

    override fun bindingInflater(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSearchBinding {
        return FragmentSearchBinding.inflate(inflater, container, false)
    }

    override fun setUiListeners() {

        binding.navigationBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.nav_to_filter_fragment -> {
                    findNavController().navigate(R.id.action_to_filters)
                    true
                }

                else -> false
            }
        }
        binding.editTextSearch.doOnTextChanged(textWatcherForEditText)
        binding.recycleViewSearchResult.addOnScrollListener(onScrollListener())
    }

    @SuppressLint("UnsafeRepeatOnLifecycleDetector")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setFragmentResultListener(KEY_FILTERS_RESULT) { requestKey, bundle ->
            isGetParamsFragment = true
            binding.editTextSearch.setText(tempValueEditText)
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.stateSearch.collect {
                    renderSearchUi(it)
                }
            }
        }
        initRecycler()
        getParamsFilter()
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

    private fun renderSearchUi(state:StateSearch) {
        when (state) {
            is StateSearch.Default -> {renderSearchDefaultUi()}

            is StateSearch.Error -> {renderSearchErrorUi()}

            is StateSearch.Loading -> {renderSearchLoadingUi()}

            is StateSearch.NoConnecting -> {renderSearchNoConnectingUi()}

            is StateSearch.Content -> {renderSearchContentUi(state.data)}

            is StateSearch.EmptyContent -> {renderSearchEmptyUi()}
        }
    }

    private fun renderSearchDefaultUi() {
        with(binding) {
            if(filterData == null){
                binding.navigationBar.menu.getItem(0).setIcon(R.drawable.ic_filters)
            }else{
                binding.navigationBar.menu.getItem(0).setIcon(R.drawable.ic_filters_selected)
            }
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

    private fun renderSearchErrorUi() {
        if (modelForQuery.page == START_PAGE_INDEX) {
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
        } else {
            with(binding) {
                infoSearchResultCount.isVisible = true
                recycleViewSearchResult.isVisible = true
                progressBar.isVisible = false
                progressBarBottom.isVisible = false
                imagePlaceholder.isVisible = false
                textPlaceholder.isVisible = false
                showToastMessage(getString(R.string.error_for_toast))
            }
        }
    }

    private fun renderSearchLoadingUi() {
        if (modelForQuery.page == START_PAGE_INDEX) {
            with(binding) {
                if(filterData == null){
                    binding.navigationBar.menu.getItem(0).setIcon(R.drawable.ic_filters)
                }else{
                    binding.navigationBar.menu.getItem(0).setIcon(R.drawable.ic_filters_selected)
                }
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
        if (modelForQuery.page == START_PAGE_INDEX) {
            with(binding) {
                if(filterData == null){
                    binding.navigationBar.menu.getItem(0).setIcon(R.drawable.ic_filters)
                }else{
                    binding.navigationBar.menu.getItem(0).setIcon(R.drawable.ic_filters_selected)
                }
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
                showToastMessage(getString(R.string.no_internet_for_toast))
                isSearchRequest = true
            }
        }
    }

    private fun renderSearchEmptyUi() {
        with(binding) {
            if(filterData == null){
                binding.navigationBar.menu.getItem(0).setIcon(R.drawable.ic_filters)
            }else{
                binding.navigationBar.menu.getItem(0).setIcon(R.drawable.ic_filters_selected)
            }
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
            if(filterData == null){
                binding.navigationBar.menu.getItem(0).setIcon(R.drawable.ic_filters)
            }else{
                binding.navigationBar.menu.getItem(0).setIcon(R.drawable.ic_filters_selected)
            }
            infoSearchResultCount.text =
                requireContext().getString(R.string.found_vacancies_count, data!!.found)
            infoSearchResultCount.isVisible = true
            _maxPage = data.maxPages
            currentPage = data.currentPages
            if (data.currentPages == START_PAGE_INDEX) {
                adapter!!.updateList(data.listVacancy, false)
            } else {
                if(!data.listVacancy.equals(tempListVacancy)){
                adapter!!.updateList(data.listVacancy, true)
                tempListVacancy = data.listVacancy
                }
            }
            isSearchRequest = true

            progressBar.isVisible = false
            progressBarBottom.isVisible = false
            imagePlaceholder.isVisible = false
            textPlaceholder.isVisible = false
            recycleViewSearchResult.isVisible = true
        }
    }

    private fun getParamsFilter(){
        filterData = viewModel.getParamsFilters()
        if(filterData != null){
            addFilterInModel(filterData!!)
        }
    }

    private fun addFilterInModel(content: FilterData) {
        with(modelForQuery) {
            page = START_PAGE_INDEX
            perPage = PER_PAGE
            area = content.idArea
            parentArea = content.idCountry
            industry = content.idIndustry
            currency = content.currency
            salary = content.salary
            onlyWithSalary = content.onlyWithSalary
        }
    }

    val textWatcherForEditText = { text: CharSequence?, start: Int, before: Int, count: Int ->
        hideIcDellText(text)
        tempValueEditText = text.toString()
        if (binding.editTextSearch.hasFocus() || isGetParamsFragment) {
            modelForQuery.text = text.toString()
            modelForQuery.page = START_PAGE_INDEX
            isGetParamsFragment = false
            viewModel.searchDebounce(modelForQuery)
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
                    editText.isEnabled = false
                    viewModel.setDefaultState()
                }
                false
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
                            if (maxPage!! - ONE_PAGE_INDEX >= currentPage + ONE_PAGE_INDEX) {
                                modelForQuery.page = currentPage + ONE_PAGE_INDEX
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

        _adapter = VacancyAdapter(listVacancy, object : VacancyAdapter.OnClickListener {
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
        binding.editTextSearch.isEnabled = true
    }

    private fun showToastMessage(message:String){
        if(isShowToast) {
            isShowToast = false
            Toast.makeText(context,message,Toast.LENGTH_LONG).show()
            lifecycleScope.launch {
                delay(TOAST_DEBOUNCE_DELAY_ML)
                isShowToast = true
               }
            }
    }
}