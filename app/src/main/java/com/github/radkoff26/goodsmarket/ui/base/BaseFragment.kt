package com.github.radkoff26.goodsmarket.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<T : ViewBinding>(@LayoutRes private val layoutId: Int) :
    Fragment(layoutId) {
    private var _binding: T? = null
    protected val binding: T
        get() = _binding!!

    abstract fun onCreateBinding(view: View): T

    @CallSuper
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = requireNotNull(super.onCreateView(inflater, container, savedInstanceState)) {
            "View was not created due to incorrect layout id!"
        }
        return view.also {
            _binding = onCreateBinding(it)
        }
    }

    @CallSuper
    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}