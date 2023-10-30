package ru.practicum.android.diploma.filter.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import ru.practicum.android.diploma.databinding.FragmentCountryBinding
import ru.practicum.android.diploma.databinding.FragmentWorkPlaceBinding

class Country : Fragment() {
    private var _binding: FragmentCountryBinding? = null
    private val binding:FragmentCountryBinding get() = _binding!!

    private fun setUiListeners(){
        with(binding){
            navigationBar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCountryBinding.inflate(inflater,container,false)

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