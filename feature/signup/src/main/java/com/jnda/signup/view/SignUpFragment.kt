package com.jnda.signup.view

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.jnda.common.view.BaseFragment
import com.jnda.signup.databinding.FragmentSignUpBinding
import com.jnda.signup.state.UserSignUpState
import com.jnda.signup.viewmodel.SignUpViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SignUpFragment : BaseFragment<FragmentSignUpBinding>() {

    private val signUpViewModel: SignUpViewModel by viewModel()

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToParent: Boolean
    ): FragmentSignUpBinding = FragmentSignUpBinding.inflate(inflater, container, attachToParent)

    override fun observeViewModels() {
        signUpViewModel.userSignUpState.observe(viewLifecycleOwner) { state ->
            if (state == UserSignUpState.SUCCESS) {
                Toast.makeText(requireContext(), "You have successfully created an account.", Toast.LENGTH_SHORT).show()
                signUpViewModel.clear()
                findNavController().popBackStack()
            } else if (state == UserSignUpState.FAILED) {
                Toast.makeText(requireContext(), "Username already exists", Toast.LENGTH_SHORT).show()
                signUpViewModel.clear()
            }
        }
    }

    override fun onViewCreated() {
        binding.tvLogin.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.tvRegister.setOnClickListener {
            signUpViewModel.addUser(
                binding.etUsername.getText(),
                binding.etPassword.getText()
            )
        }

        updateButtonState()
        binding.etUsername.onAfterTextChanged { updateButtonState() }
        binding.etPassword.onAfterTextChanged { updateButtonState() }
    }

    private fun updateButtonState() {
        val username = binding.etUsername.getText()
        val password = binding.etPassword.getText()

        binding.tvRegister.updateButtonState(username.isNotEmpty() && password.isNotEmpty())
    }

}