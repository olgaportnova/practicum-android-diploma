package ru.practicum.android.diploma

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.practicum.android.diploma.databinding.FragmentWorkPlaceBinding

class WorkPlace : Fragment() {
    private var _binding: FragmentWorkPlaceBinding? = null
    private val binding:FragmentWorkPlaceBinding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_work_place, container, false)
    }

}