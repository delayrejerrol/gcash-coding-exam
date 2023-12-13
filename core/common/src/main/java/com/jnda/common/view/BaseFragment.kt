package com.jnda.common.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.jnda.common.R
import com.jnda.common.provider.NavigationProvider
import org.koin.android.ext.android.inject

abstract class BaseFragment<VB: ViewBinding> : Fragment() {

    val navigationProvider: NavigationProvider by inject()

    private var _binding : VB? = null
    val binding get() = _binding!!

    abstract fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?, attachToParent: Boolean) : VB
    abstract fun observeViewModels()
    abstract fun onViewCreated()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = getViewBinding(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeViewModels()
        onViewCreated()
    }

    fun NavDeepLinkRequest.navigate(popBackStack: Boolean = false) {
        if (popBackStack) {
            findNavController().popBackStack()
        }
        findNavController().navigate(this, getNavOption())
    }

    private fun getNavOption(): NavOptions {
        return NavOptions.Builder()
            .setEnterAnim(R.anim.h_fragment_enter_right)
            .setExitAnim(R.anim.h_fragment_exit)
            .setPopEnterAnim(R.anim.h_fragment_pop_enter)
            .setPopExitAnim(R.anim.h_fragment_enter_left_out)
            .build()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}