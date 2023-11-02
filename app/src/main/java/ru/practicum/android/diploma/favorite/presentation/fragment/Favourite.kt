package ru.practicum.android.diploma.favorite.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFavouriteBinding
import ru.practicum.android.diploma.favorite.domain.FavoriteState
import ru.practicum.android.diploma.favorite.presentation.view_model.FavoriteViewModel

class Favourite : Fragment() {
    private var _binding: FragmentFavouriteBinding? = null
    private val binding get() = _binding!!

    private val favouritesViewModel: FavoriteViewModel by viewModel()
    private var isClickAllowed = true

    //TODO: adapter
    //   private var vacanciesAdapter: RecycleViewVacancyAdapter? = null

    private fun setUiListeners() {
        with(binding) {
//            txtTemporal.setOnClickListener {
//                openFragmentVacancy(vacancyToShow = "Вакансия из избранного")
//            }
        }
    }

    private fun openFragmentVacancy(vacancyToShow: String) {
        findNavController().navigate(
            R.id.action_favourite_to_vacancy,
            Bundle().apply { putString("vacancy_model", vacancyToShow) })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavouriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUiListeners()
        // create adapter && привязать к binding

        favouritesViewModel.stateLiveData().observe(viewLifecycleOwner) { state ->
            render(state)
        }
        favouritesViewModel.getFavorites()

        isClickAllowed = true

    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        // обнулить adapter
    }

    private fun updateUI(state: FavoriteState) {
        when (state) {
            is FavoriteState.Success -> showFavorite(state.vacancies)
            is FavoriteState.EmptyList -> showEmpty()
            is FavoriteState.Error -> showError()
        }
    }

    private fun showFavorite(listOfFavorite: List<Vacancy>) {
        with(binding) {
            vacanciesAdapter?.items = listOfFavorite
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

    private fun isClickDebounce(): Boolean {
        val current = isClickAllowed
        if (isClickAllowed) {
            isClickAllowed = false
            viewLifecycleOwner.lifecycleScope.launch {
                delay(CLICK_DEBOUNCE_DELAY_MILLIS)
                isClickAllowed = true
            }
        }
        return current
    }


}
