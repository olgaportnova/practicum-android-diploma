package ru.practicum.android.diploma

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.practicum.android.diploma.databinding.FragmentVacancyBinding

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_VACANCY = "vacancy_model"

/**
 * A simple [Fragment] subclass.
 * Use the [Vacancy.newInstance] factory method to
 * create an instance of this fragment.
 */
class Vacancy : Fragment() {
    // TODO: Rename and change types of parameters
    private var vacancy: String? = null

    private lateinit var _binding: FragmentVacancyBinding
    private val binding get() = _binding!!

    private fun setUiListeners() {
        with(binding) {
            navigationBar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }

            btnSimilar.setOnClickListener {
                findNavController().navigate(R.id.action_to_similar)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            vacancy = it.getString(ARG_VACANCY)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVacancyBinding.inflate(inflater, container, false)

        vacancy?.let {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUiListeners()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Vacancy model
         * @return A new instance of fragment Vacancy.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(vacancyModel: String) =
            Vacancy().apply {
                arguments = Bundle().apply {
                    putString(ARG_VACANCY, vacancyModel)
                }
            }
    }
}