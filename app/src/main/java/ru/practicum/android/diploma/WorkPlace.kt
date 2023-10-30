package ru.practicum.android.diploma

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.practicum.android.diploma.databinding.FragmentWorkPlaceBinding

class WorkPlace : Fragment() {
    private var _binding: FragmentWorkPlaceBinding? = null
    private val binding: FragmentWorkPlaceBinding get() = _binding!!

    private fun setUiListeners() {
        with(binding) {
            navigationBar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }

            btnChooseCountry.setOnClickListener {
                findNavController().navigate(R.id.action_to_country)
            }

            btnChooseDistrict.setOnClickListener {
                findNavController().navigate(
                    R.id.action_to_district,
                    Bundle().apply { putInt(ARG_COUNTRY_ID, 555) })
            }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWorkPlaceBinding.inflate(inflater, container, false)

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