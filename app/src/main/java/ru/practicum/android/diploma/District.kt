package ru.practicum.android.diploma

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.practicum.android.diploma.databinding.FragmentDistrictBinding
const val ARG_COUNTRY_ID = "country_id_pram"

/**
 * A simple [Fragment] subclass.
 * Use the [District.newInstance] factory method to
 * create an instance of this fragment and set required param
 */
open class District : Fragment() {
    private var _binding: FragmentDistrictBinding? = null
    private val binding:FragmentDistrictBinding get() = _binding!!

    // TODO: Rename and change types of parameters
    private var countryId: Int? = null

    private fun setUiListeners(){
        with(binding){
            navigationBar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            countryId = it.getInt(ARG_COUNTRY_ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDistrictBinding.inflate(inflater,container,false)

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUiListeners()

        if (countryId==null){
            binding.navigationBar.title =resources.getString(R.string.country_fragment_title)
        }
        else{
            Toast.makeText(requireContext(),"Country id = $countryId",Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object{
        fun newInstance(countryId:Int): District {
            return District().apply {
                arguments=Bundle().apply { putInt(ARG_COUNTRY_ID,countryId) }
            }
        }
    }
}