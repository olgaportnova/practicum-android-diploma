package ru.practicum.android.diploma.filter.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import ru.practicum.android.diploma.DefaultFragment
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentDistrictBinding
import kotlin.random.Random

const val ARG_COUNTRY_ID = "country_id_pram"
const val KEY_DISTRICT_RESULT = "district_result"
const val DISTRICT_NAME = "district_name_param"
const val DISTRICT_ID = "district_id_param"

/**
 * A simple [Fragment] subclass.
 * Use the [District.newInstance] factory method to
 * create an instance of this fragment and set required param
 */
open class District : DefaultFragment<FragmentDistrictBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentDistrictBinding =
        FragmentDistrictBinding::inflate

    // TODO: Rename and change types of parameters
    private var countryId: Int? = null

    override fun setUiListeners(){
        with(binding){
            navigationBar.setNavigationOnClickListener {
                exitExtraWhenSystemBackPushed()
            }
        }
    }

    override fun exitExtraWhenSystemBackPushed() {
        // Для поиска вакансии по региону необходимо передать в поисковый запрос id региона
        // Параметр area - Регион. Необходимо передавать id из справочника /areas. Можно указать несколько значений
        val area = Bundle().apply {
            putInt(DISTRICT_ID, Random.nextInt(10000))
            putString(DISTRICT_NAME,"Бутово")
        }
        setFragmentResult(KEY_DISTRICT_RESULT,area)
        findNavController().popBackStack()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            countryId = it.getInt(ARG_COUNTRY_ID)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (countryId==null){
            binding.navigationBar.title =resources.getString(R.string.country_fragment_title)
        }
        else{
            Toast.makeText(requireContext(),"Country id = $countryId",Toast.LENGTH_SHORT).show()
        }
    }

    companion object{
        fun newInstance(countryId:Int): District {
            return District().apply {
                arguments=Bundle().apply { putInt(ARG_COUNTRY_ID,countryId) }
            }
        }
    }
}