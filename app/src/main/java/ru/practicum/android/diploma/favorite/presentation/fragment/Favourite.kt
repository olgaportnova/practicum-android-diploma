package ru.practicum.android.diploma.favorite.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFavouriteBinding

class Favourite : Fragment() {
    private var _binding: FragmentFavouriteBinding? = null
    private val binding get() = _binding!!

    private fun setUiListeners() {
        with(binding) {
//            txtTemporal.setOnClickListener {
//                openFragmentVacancy(vacancyToShow = "Вакансия из избранного")
//            }

            navigationBar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    /**
     * Function to open required vacancy details
     * @param vacancyToShow represent vacancy model
     * @author Oleg
     */
    // TODO: change param type
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

}