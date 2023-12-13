package com.jnda.signin.view

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import com.jnda.common.view.BaseFragment
import com.jnda.signin.databinding.FragmentSignInBinding
import com.jnda.signin.state.UserSignInState
import com.jnda.signin.viewmodel.SignInViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SignInFragment : BaseFragment<FragmentSignInBinding>() {

    private val signInViewModel: SignInViewModel by viewModel()

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToParent: Boolean
    ): FragmentSignInBinding =
        FragmentSignInBinding.inflate(inflater, container, attachToParent)

    override fun observeViewModels() {
        signInViewModel.userSignInState.observe(viewLifecycleOwner) { state ->
            if (state == UserSignInState.SUCCESS) {
                binding.etUsername.clearText()
                binding.etPassword.clearText()
                // NAVIGATE TO HOME SCREEN
                navigationProvider.navigateToHomeScreen().navigate(popBackStack = true)
                signInViewModel.clear()
            } else if (state == UserSignInState.FAILED) {
                Toast.makeText(requireContext(), "Invalid username or password", Toast.LENGTH_SHORT).show()
                signInViewModel.clear()
            }
        }
    }

    override fun onViewCreated() {
        binding.tvLogin.setOnClickListener {
            // VERIFY USERNAME AND PASSWORD
            signInViewModel.signIn(
                binding.etUsername.getText(),
                binding.etPassword.getText()
            )
        }
        binding.tvRegister.setOnClickListener {
            // NAVIGATE TO REGISTER SCREEN
            navigationProvider.navigateToSignUpScreen().navigate()
        }

        updateButtonState()
        binding.etUsername.onAfterTextChanged { updateButtonState() }
        binding.etPassword.onAfterTextChanged { updateButtonState() }
    }

    private fun updateButtonState() {
        val username = binding.etUsername.getText()
        val password = binding.etPassword.getText()

        binding.tvLogin.updateButtonState(username.isNotEmpty() && password.isNotEmpty())
    }
}