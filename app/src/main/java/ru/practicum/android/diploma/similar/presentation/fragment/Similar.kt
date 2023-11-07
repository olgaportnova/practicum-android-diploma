package ru.practicum.android.diploma.similar.presentation.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.util.DefaultFragment
import ru.practicum.android.diploma.databinding.FragmentSimilarBinding
import ru.practicum.android.diploma.favorite.domain.FavoriteState
import ru.practicum.android.diploma.search.domain.models.Vacancy

class Similar : DefaultFragment<FragmentSimilarBinding>() {

    override fun bindingInflater(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSimilarBinding {
        return FragmentSimilarBinding.inflate(inflater, container, false)
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


}