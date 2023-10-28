package ru.practicum.android.diploma

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.practicum.android.diploma.databinding.FragmentFiltersBinding

class Filters : Fragment() {
    private lateinit var _binding: FragmentFiltersBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFiltersBinding.inflate(inflater, container, false)


        // Inflate the layout for this fragment
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding.navigationBar.setNavigationOnClickListener {
            //parentFragmentManager.setFragmentResult("key", Bundle().apply { putString("bundleKey","Возврат с фрагмента Фильтры. Можно выполнить любую функцию") })
            findNavController().popBackStack()
        }
    }


}