package ru.practicum.android.diploma.util

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

abstract class DefaultFragment<T : ViewBinding> : Fragment() {
    private var _binding: T? = null
    protected val binding: T get() = _binding!!

    abstract fun bindingInflater(inflater: LayoutInflater, container: ViewGroup?): T

    protected val backPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            exitExtraWhenSystemBackPushed()
        }
    }

    abstract fun setUiListeners()

    open fun exitExtraWhenSystemBackPushed() {
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        requireActivity().onBackPressedDispatcher.addCallback(
            this, // LifecycleOwner
            backPressedCallback
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = bindingInflater(inflater, container)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUiListeners()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun showMsgDialog(message: String) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Message Dialog")
            .setMessage(message)
            .setNeutralButton("OK", null)
            .show()
    }
}