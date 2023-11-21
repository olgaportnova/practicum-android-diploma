package ru.practicum.android.diploma.team.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentCrewBinding
import ru.practicum.android.diploma.util.DefaultFragment

class Crew : DefaultFragment<FragmentCrewBinding>() {
    override fun bindingInflater(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCrewBinding {
        return FragmentCrewBinding.inflate(inflater,container,false)
    }
    override fun setUiListeners() {}

    override fun exitExtraWhenSystemBackPushed() {
        parentFragmentManager.primaryNavigationFragment?.requireView()
            ?.findViewById<ViewPager2>(R.id.vp2)?.currentItem = 0
      }
    }

