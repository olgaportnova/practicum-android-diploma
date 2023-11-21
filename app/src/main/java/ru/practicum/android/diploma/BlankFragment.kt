package ru.practicum.android.diploma

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import ru.practicum.android.diploma.databinding.FragmentBlankBinding
import ru.practicum.android.diploma.favorite.presentation.fragment.Favourite
import ru.practicum.android.diploma.search.presentation.fragment.Search
import ru.practicum.android.diploma.team.presentation.Crew
import ru.practicum.android.diploma.util.DefaultFragment
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

        binding.vp2.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.rootNavBar.menu.getItem(position).isChecked = true
            }
        })

    }

    private fun navigateToFragment(num: Int): Boolean {
        binding.vp2.currentItem = num
        //binding.rootNavBar.menu.getItem(num).isChecked = true
        return true
    }

    override fun setUiListeners() {
        binding.rootNavBar.setOnItemSelectedListener {
            //showStack()
            when (it.itemId) {
                R.id.crew -> navigateToFragment(2)
                R.id.favourite -> navigateToFragment(1)
                R.id.search -> navigateToFragment(0)
                else -> false
            }
        }
    }

    override fun exitExtraWhenSystemBackPushed() {
        if (binding.vp2.currentItem != 0) navigateToFragment(0)
        else {
            showMsgDialog("exit?")
            // TODO: add exit app fun
        }
    }

   /* private fun showStack() {
        val stack = parentFragmentManager.backStackEntryCount
        //val name  = parentFragmentManager.getBackStackEntryAt(0).name
        // Snackbar.make(binding.rootNavBar,name.toString(),Snackbar.LENGTH_SHORT).show()
    }*/

    class DemoCollectionAdapter(
        activityFr: FragmentActivity,
        private val fragmentMap: Map<String, Fragment>
    ) :
        FragmentStateAdapter(activityFr) {
        override fun getItemCount() = fragmentMap.size
        override fun createFragment(position: Int) = fragmentMap.values.elementAt(position)
    }
}