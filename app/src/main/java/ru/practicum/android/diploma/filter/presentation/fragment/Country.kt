package ru.practicum.android.diploma.filter.presentation.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.filter.presentation.view_model.CountryVm
import kotlin.random.Random

const val KEY_COUNTRY_RESULT = "country_result"
const val COUNTRY_NAME = "country_name_param"
const val COUNTRY_ID = "country_id_param"
class Country : District() {
    lateinit var vm:CountryVm
    override fun exitExtraWhenSystemBackPushed() {
        // Для поиска региона необходимо название страны или ее id
        // При выходе с фрагмента передаем родительскому фрагменту параметры выбранной страны
        val county = Bundle().apply {
            putInt(COUNTRY_ID, Random.nextInt(10000))
            putString(COUNTRY_NAME,"Зимбабве")
        }
        setFragmentResult(KEY_COUNTRY_RESULT,county)
        findNavController().popBackStack()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vm=ViewModelProvider(this)[CountryVm::class.java]

        setObservers()
    }
    private fun setObservers(){
        vm.errorMsg.observe(viewLifecycleOwner){
            showMsgDialog(it)
        }

        lifecycleScope.launch {
            vm.screenState.flowWithLifecycle(lifecycle,Lifecycle.State.STARTED).collect {
                when(it){
                    is DistrictScreenState.Loading -> binding.txtContent.text = "LOADING"
                    is DistrictScreenState.Success ->{
                        binding.txtContent.text = it.data.map { country -> "${country.name}\n" } .toString()
                    }
                    else ->{}
                }
            }
        }
    }
}