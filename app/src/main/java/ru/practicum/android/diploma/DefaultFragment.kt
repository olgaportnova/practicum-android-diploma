package ru.practicum.android.diploma

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class  DefaultFragment<T:ViewBinding>:Fragment() {
    private var _binding:T? = null
    abstract val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> T
    protected val binding:T get() = _binding!!

    /**
     * Set all ui listeners here
     */
    abstract fun setUiListeners()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = bindingInflater.invoke(inflater,container,false)
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
}