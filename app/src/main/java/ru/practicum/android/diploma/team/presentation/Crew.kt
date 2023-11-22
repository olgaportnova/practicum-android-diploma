package ru.practicum.android.diploma.team.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.practicum.android.diploma.databinding.FragmentCrewBinding
import ru.practicum.android.diploma.util.DefaultFragment

class Crew : DefaultFragment<FragmentCrewBinding>() {
    override fun bindingInflater(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCrewBinding {
        return FragmentCrewBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        backPressedCallback.remove()
    }

    override fun setUiListeners() {}
}



