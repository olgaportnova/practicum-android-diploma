package ru.practicum.android.diploma.favorite.presentation.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFavouriteBinding
import ru.practicum.android.diploma.databinding.FragmentSimilarBinding
import ru.practicum.android.diploma.favorite.domain.FavoriteState
import ru.practicum.android.diploma.favorite.presentation.view_model.FavoriteViewModel
import ru.practicum.android.diploma.favorite.recycle_view.VacancyAdapter
import ru.practicum.android.diploma.search.domain.models.Vacancy
import ru.practicum.android.diploma.util.DefaultFragment

private const val ARG_VACANCY = "vacancy_model"
class Favourite : DefaultFragment<FragmentFavouriteBinding>() {

    private val viewModel: FavoriteViewModel by viewModel()
    private lateinit var adapter: VacancyAdapter
    override fun bindingInflater(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentFavouriteBinding {
        return FragmentFavouriteBinding.inflate(inflater, container, false)
    }

    @SuppressLint("UnsafeRepeatOnLifecycleDetector")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        setUiListeners()

      lifecycleScope.launch {
          repeatOnLifecycle(Lifecycle.State.STARTED) {
              viewModel.screenState.collect {
                  updateUI(it)
              }
          }
      }
        viewModel.getAllFavoriteVacancies()
    }
    private fun initRecyclerView() {
        adapter = VacancyAdapter(arrayListOf(), object : VacancyAdapter.OnClickListener {
            override fun onItemClick(vacancy: Vacancy) {
                openFragmentVacancy(vacancyToShow = vacancy.id)
            }
        })
        binding.favouritesRecyclerView.adapter = adapter
        binding.favouritesRecyclerView.layoutManager = LinearLayoutManager(context)
    }
    private fun openFragmentVacancy(vacancyToShow: Int) {
        findNavController().navigate(
            R.id.action_favourite_to_vacancy,
            Bundle().apply { putInt(ARG_VACANCY, vacancyToShow) })
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
            is FavoriteState.Success -> showFavorite(state.vacancies)
            is FavoriteState.EmptyList -> showEmpty()
            is FavoriteState.Error -> showError()
            else -> {}
        }
    }
    private fun showFavorite(listOfFavorite: List<Vacancy>) {
        with(binding) {
            adapter.updateList(listOfFavorite)
            favouritesRecyclerView.visibility = View.VISIBLE
            imagePlaceholder.visibility = View.GONE
            textPlaceholder.visibility = View.GONE
        }
    }
    private fun showError() {
        with(binding) {
            favouritesRecyclerView.visibility = View.GONE
            imagePlaceholder.setImageResource(R.drawable.placeholder_empty_result)
            imagePlaceholder.visibility = View.VISIBLE
            textPlaceholder.setText(R.string.no_search_result)
            textPlaceholder.visibility = View.VISIBLE
        }
    }
    private fun showEmpty() {
        with(binding) {
            favouritesRecyclerView.visibility = View.GONE
            imagePlaceholder.setImageResource(R.drawable.placeholder_favorite_empty)
            imagePlaceholder.visibility = View.VISIBLE
            textPlaceholder.setText(R.string.empty_list_placeholder_favorite)
            textPlaceholder.visibility = View.VISIBLE
        }
    }
}
