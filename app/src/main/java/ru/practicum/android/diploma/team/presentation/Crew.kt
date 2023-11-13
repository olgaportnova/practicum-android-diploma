package ru.practicum.android.diploma.team.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.practicum.android.diploma.databinding.FragmentCrewBinding

class Crew : Fragment() {
    private  var _binding: FragmentCrewBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCrewBinding.inflate(inflater,container,false)
        return binding.root
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
}