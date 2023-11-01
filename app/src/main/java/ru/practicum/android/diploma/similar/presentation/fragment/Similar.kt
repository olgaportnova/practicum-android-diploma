package ru.practicum.android.diploma.similar.presentation.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import ru.practicum.android.diploma.DefaultFragment
import ru.practicum.android.diploma.databinding.FragmentSimilarBinding

class Similar : DefaultFragment<FragmentSimilarBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSimilarBinding =
        FragmentSimilarBinding::inflate

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