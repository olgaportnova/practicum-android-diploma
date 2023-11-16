package ru.practicum.android.diploma.filter.presentation.util

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentDistrictBinding
import ru.practicum.android.diploma.filter.recycler.AreaAdapter
import ru.practicum.android.diploma.util.DefaultFragment

const val ARG_COUNTRY_ID = "country_id_pram"
const val ARG_INDUSTRY_ID = "industry_id_pram"
const val KEY_FILTERS_RESULT = "filters_result"

open class ParentDataFragment : DefaultFragment<FragmentDistrictBinding>() {
    protected var paramCountryId: Int? = null // Считывается из аргументов в onCreate
    open val vm: DefaultViewModel? = null

    protected var adapter = AreaAdapter(mutableListOf()) {
        vm?.dataToSendBack = it
        exitExtraWhenSystemBackPushed() // Exit after choosing required area
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

            txtSearch.doOnTextChanged { text, start, before, count ->
                editTextDrawableEnd(text)
            }

            btnChooseAll.setOnClickListener {
                exitExtraWhenSystemBackPushed()
            }


        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun editTextDrawableEnd(text: CharSequence?) {
        if (text.isNullOrEmpty()) {
            binding.txtSearch.setCompoundDrawablesWithIntrinsicBounds(
                0,
                0,
                R.drawable.ic_search,
                0
            )
            binding.txtSearch.setOnTouchListener { _, motionEvent ->
                false
            }
        } else {
            binding.txtSearch.setCompoundDrawablesWithIntrinsicBounds(
                0,
                0,
                R.drawable.ic_clear,
                0
            )
            val iconClear = binding.txtSearch.compoundDrawables[2]
            binding.txtSearch.setOnTouchListener { _, motionEvent ->
                if ((motionEvent.action == MotionEvent.ACTION_UP) &&
                    (motionEvent.rawX >= (binding.txtSearch.right - iconClear.bounds.width() * 2))
                ) {
                    binding.txtSearch.setText("")
                    binding.txtSearch.setCompoundDrawablesWithIntrinsicBounds(
                        0,
                        0,
                        R.drawable.ic_search,
                        0
                    )
                    vm?.txtSearchChanged("")
                }
                true
            }
            vm?.txtSearchChanged(binding.txtSearch.text)
        }
    }

    open fun setObservers() {
        vm?.let { it ->
            with(it) {
                errorMsg.observe(viewLifecycleOwner) { msg -> showMsgDialog(msg) }

                itemPosToUpdate.observe(viewLifecycleOwner) { item ->
                    adapter.notifyItemChanged(item)
                    // После выбора хотя бы одного элемента отображаем кнопку "Выбрать"
                    binding.btnChooseAll.isVisible = true
                }
            }
        }
    }

    open fun observeFlowScreenState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm?.screenState?.collect {
                    setFragmentScreenState(it)
                }
            }
        }
    }

    override fun exitExtraWhenSystemBackPushed() {
        findNavController().popBackStack()
    }

    private fun setUpAdapter() {
        binding.areaRecycler.adapter = adapter
        binding.areaRecycler.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpAdapter()
        setUiListeners()
        setObservers()
        observeFlowScreenState()
    }

    private fun setFragmentScreenState(newScreenState: ScreenState) {
        Log.d("status", newScreenState.toString())
        when (newScreenState) {

            is ScreenState.Content -> {
                // TODO: need to do in background
                adapter.changeData(newScreenState.data)
                binding.areaRecycler.isVisible = true
                binding.imagePlaceholder.isVisible = false
                binding.textPlaceholderEmptyList.isVisible = false
                binding.progressBar.isVisible = false
                binding.textPlaceholderError.isVisible = false
            }

            is ScreenState.Loading -> {
                binding.progressBar.isVisible = true
                binding.areaRecycler.isVisible = false
                binding.imagePlaceholder.isVisible = false
                binding.textPlaceholderEmptyList.isVisible = false
                binding.textPlaceholderError.isVisible = false
            }

            is ScreenState.EmptyContent -> {
                Log.d("status", newScreenState.code.toString())
                binding.areaRecycler.isVisible = false
                binding.imagePlaceholder.isVisible = true
                binding.textPlaceholderEmptyList.isVisible = true
                binding.textPlaceholderError.isVisible = false
                binding.imagePlaceholder.setImageResource(R.drawable.placeholder_empty_result)
                binding.progressBar.isVisible = false
            }

            is ScreenState.Error ->  {
                binding.areaRecycler.isVisible = false
                binding.imagePlaceholder.isVisible = true
                binding.textPlaceholderError.isVisible = true
                binding.imagePlaceholder.setImageResource(R.drawable.placeholder_enable_to_get_list_region)
                binding.textPlaceholderError.setText(R.string.enable_to_get_list)
                binding.progressBar.isVisible = false
            }
            else -> {}
        }
    }
}