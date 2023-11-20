package ru.practicum.android.diploma

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import ru.practicum.android.diploma.databinding.FragmentBlankBinding
import ru.practicum.android.diploma.favorite.presentation.fragment.Favourite
import ru.practicum.android.diploma.search.presentation.fragment.Search
import ru.practicum.android.diploma.team.presentation.Crew
import ru.practicum.android.diploma.util.DefaultFragment


class BlankFragment : DefaultFragment<FragmentBlankBinding>() {

    // Хранение фрагментов внутри коллекции fragmentMap
    private val fragmentMap = mutableMapOf<String, Fragment>()

    override fun bindingInflater(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentBlankBinding {
        return FragmentBlankBinding.inflate(inflater,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Заполняем коллекцию нужными фрагментами
        fragmentMap[getString(R.string.search_fragment_title)] = Search()
        fragmentMap[getString(R.string.favourite)] = Favourite()
        fragmentMap[getString(R.string.crew)] = Crew()

        // Динамически создаем TabItems
        fragmentMap.keys.forEach {
            binding.tabs.addTab(binding.tabs.newTab().setText(it))
        }

        binding.vp2.adapter = DemoCollectionAdapter(requireActivity(),fragmentMap)

        TabLayoutMediator(
            binding.tabs,
            binding.vp2
        ) { tab, pos ->
            tab.text = fragmentMap.keys.elementAt(pos)
        }.attach()
    }


    override fun setUiListeners() {
        //TODO("Not yet implemented")
    }

    class DemoCollectionAdapter(activityFr: FragmentActivity, private val fragmentMap: Map<String, Fragment>) :
        FragmentStateAdapter(activityFr) {
        override fun getItemCount() = fragmentMap.size
        override fun createFragment(position: Int) = fragmentMap.values.elementAt(position)
    }
}