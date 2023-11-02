package ru.practicum.android.diploma.filter.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.DefaultFragment
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentDistrictBinding
import ru.practicum.android.diploma.filter.domain.models.Area
import ru.practicum.android.diploma.filter.domain.models.Country
import ru.practicum.android.diploma.filter.presentation.view_model.DistrictVm
import ru.practicum.android.diploma.filter.recycler.AreaAdapter

const val ARG_COUNTRY_ID = "country_id_pram"
const val KEY_DISTRICT_RESULT = "district_result"
const val DISTRICT_DATA = "district_id_param"

/**
 * A simple [Fragment] subclass.
 * Use the [District.newInstance] factory method to
 * create an instance of this fragment and set required param
 */
open class District : DefaultFragment<FragmentDistrictBinding>() {
    // TODO: Rename and change types of parameters
    private var countryId: Int? = null

    lateinit var vm: DistrictVm

    private val adapter = AreaAdapter(mutableListOf()) {
        showMsgDialog(it.toString())
        vm.areaToSendBack = Country(it.name, it.id, "")
    }

    override fun bindingInflater(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentDistrictBinding {
        return FragmentDistrictBinding.inflate(inflater, container, false)
    }

    override fun setUiListeners() {
        with(binding) {
            navigationBar.setNavigationOnClickListener {
                exitExtraWhenSystemBackPushed()
            }
        }
    }

    override fun exitExtraWhenSystemBackPushed() {
        // Для поиска вакансии по региону необходимо передать в поисковый запрос id региона
        // Параметр area - Регион. Необходимо передавать id из справочника /areas. Можно указать несколько значений
        val area = Bundle().apply {
            putParcelable(DISTRICT_DATA, vm.areaToSendBack)
        }
        setFragmentResult(KEY_DISTRICT_RESULT, area)
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
        vm = ViewModelProvider(this)[DistrictVm::class.java]
        setObservers() // Обработчики lifeData
        setUpAdapter() // Настройка адаптера для RecyclerView

        if (countryId == null) {
            binding.navigationBar.title = resources.getString(R.string.country_fragment_title)
            vm.loadCountryList()
        } else {
            // Загрузка списка регионов производится только при наличии ненулевого id страны
            //Toast.makeText(requireContext(), "Country id = $countryId", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setUpAdapter() {
        binding.areaRecycler.adapter = adapter
        binding.areaRecycler.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun setObservers() {
        vm.errorMsg.observe(viewLifecycleOwner) { showMsgDialog(it) }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.screenState.collect { setFragmentScreenState(it) }
            }
        }
    }

    private fun setFragmentScreenState(newScreenState: DistrictScreenState) {
        when (newScreenState) {
            is DistrictScreenState.Loading -> binding.txtContent.text = "Load district "
            is DistrictScreenState.ContentCountry -> adapter.changeData(newScreenState.data.map { country ->
                Area(country.id, null, country.name, emptyList())
            })

            else -> {}
        }
    }

    companion object {
        fun newInstance(countryId: Int): District {
            return District().apply {
                arguments = Bundle().apply { putInt(ARG_COUNTRY_ID, countryId) }
            }
        }
    }
}