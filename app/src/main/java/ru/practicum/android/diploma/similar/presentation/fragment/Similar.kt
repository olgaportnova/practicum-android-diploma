package ru.practicum.android.diploma.similar.presentation.fragment

import android.annotation.SuppressLint
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
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.util.DefaultFragment
import ru.practicum.android.diploma.databinding.FragmentSimilarBinding
import ru.practicum.android.diploma.favorite.domain.FavoriteState
import ru.practicum.android.diploma.favorite.recycle_view.VacancyAdapter
import ru.practicum.android.diploma.search.domain.models.Vacancy
import ru.practicum.android.diploma.similar.presentation.view_model.SimilarViewModel

class Similar : DefaultFragment<FragmentSimilarBinding>() {

    private val similarViewModel by viewModel<SimilarViewModel>()
    private var vacancyId: String? = null
    private lateinit var adapter: VacancyAdapter

    override fun bindingInflater(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSimilarBinding {
        return FragmentSimilarBinding.inflate(inflater, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            vacancyId = it.getString(ARG_VACANCY)
        }

    }

    @SuppressLint("UnsafeRepeatOnLifecycleDetector")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                similarViewModel.screenState.collect {
                    updateUI(it)

                }
            }
        }

        if (vacancyId != null) {
            vacancyId?.let { id -> similarViewModel.getSimilarVacancies(id) }
        }
        initRecyclerView()

        setUiListeners()

    }

    private fun initRecyclerView() {
        adapter = VacancyAdapter(arrayListOf(), object : VacancyAdapter.OnClickListener {
            override fun onItemClick(vacancy: Vacancy) {
            }
        })
        binding.recycleViewSimilarSearchResult.adapter = adapter
        binding.recycleViewSimilarSearchResult.layoutManager = LinearLayoutManager(context)
    }

    override fun setUiListeners() {
        with(binding) {
            navigationBar.setNavigationOnClickListener {
                exitExtraWhenSystemBackPushed()
            }
        }
    }

    override fun exitExtraWhenSystemBackPushed() {
        findNavController().popBackStack()
    }

    private fun updateUI(state: FavoriteState) {
        when (state) {
            is FavoriteState.Loading -> showLoading()
            is FavoriteState.Error -> showError()
            is FavoriteState.NoInternet -> showNoInternet()
            is FavoriteState.Success -> showSimilar(state.vacancies)
            else -> {}
        }
    }

    private fun showSimilar(listOfSimilar: List<Vacancy>) {
        with(binding) {
            adapter.updateList(listOfSimilar)
            recycleViewSimilarSearchResult.visibility = View.VISIBLE
            imagePlaceholder.visibility = View.GONE
            textPlaceholder.visibility = View.GONE
            progressBar.visibility = View.GONE
        }
    }

    private fun showError() {
        with(binding) {
            recycleViewSimilarSearchResult.visibility = View.GONE
            imagePlaceholder.setImageResource(R.drawable.placeholder_error_server)
            imagePlaceholder.visibility = View.VISIBLE
            textPlaceholder.setText(R.string.server_error)
            textPlaceholder.visibility = View.VISIBLE
            progressBar.visibility = View.GONE
        }
    }

    private fun showLoading() {
        with(binding) {
            recycleViewSimilarSearchResult.visibility = View.GONE
            imagePlaceholder.visibility = View.GONE
            textPlaceholder.visibility = View.GONE
            progressBar.visibility = View.VISIBLE
        }
    }

    private fun showNoInternet() {
        with(binding) {
            recycleViewSimilarSearchResult.visibility = View.GONE
            imagePlaceholder.setImageResource(R.drawable.placeholder_no_internet)
            imagePlaceholder.visibility = View.VISIBLE
            textPlaceholder.setText(R.string.no_internet)
            textPlaceholder.visibility = View.VISIBLE
            progressBar.visibility = View.GONE
        }
    }
    companion object {
        private const val ARG_VACANCY = "vacancy_model"
    }
}