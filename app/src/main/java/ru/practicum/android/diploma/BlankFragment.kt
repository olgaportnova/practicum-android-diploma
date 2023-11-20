package ru.practicum.android.diploma

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import ru.practicum.android.diploma.databinding.FragmentBlankBinding
import ru.practicum.android.diploma.favorite.presentation.fragment.Favourite
import ru.practicum.android.diploma.search.presentation.fragment.Search
import ru.practicum.android.diploma.team.presentation.Crew
import ru.practicum.android.diploma.util.DefaultFragment
import kotlin.collections.Map
import kotlin.collections.elementAt
import kotlin.collections.mutableMapOf
import kotlin.collections.set


class BlankFragment : DefaultFragment<FragmentBlankBinding>() {

    // Хранение фрагментов внутри коллекции fragmentMap
    private val fragmentMap = mutableMapOf<String, Fragment>()

    private lateinit var adapter: DemoCollectionAdapter

    override fun bindingInflater(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentBlankBinding {
        return FragmentBlankBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Заполняем коллекцию нужными фрагментами
        fragmentMap[getString(R.string.search_fragment_title)] = Search()
        fragmentMap[getString(R.string.favourite)] = Favourite()
        fragmentMap[getString(R.string.crew)] = Crew()

        adapter = DemoCollectionAdapter(requireActivity(), fragmentMap)

        binding.vp2.adapter = adapter
    }

    private fun setCurrentFragment(num: Int): Boolean {
        binding.vp2.currentItem = num
        binding.rootNavBar.menu.getItem(num).isChecked = true

        return true
    }

    override fun setUiListeners() {
        binding.rootNavBar.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.crew -> setCurrentFragment(2)
                R.id.favourite -> setCurrentFragment(1)
                R.id.search -> setCurrentFragment(0)
                else -> false
            }
        }
    }

    class DemoCollectionAdapter(
        activityFr: FragmentActivity,
        private val fragmentMap: Map<String, Fragment>
    ) :
        FragmentStateAdapter(activityFr) {
        override fun getItemCount() = fragmentMap.size
        override fun createFragment(position: Int) = fragmentMap.values.elementAt(position)
    }
}