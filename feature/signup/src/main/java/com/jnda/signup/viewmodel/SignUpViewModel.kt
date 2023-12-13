package com.jnda.signup.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jnda.common.viewmodel.BaseViewModel
import com.jnda.signup.data.repository.SignUpRepository
import com.jnda.signup.state.UserSignUpState
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class SignUpViewModel(
    private val signUpRepository: SignUpRepository
) : BaseViewModel() {

    private val _userSignUpState: MutableLiveData<UserSignUpState> by lazy { MutableLiveData() }
    val userSignUpState: LiveData<UserSignUpState> = _userSignUpState

    fun addUser(username: String, password: String) {
        signUpRepository.insertUser(username, password).also { result ->
            if (result > 0) {
                _userSignUpState.postValue(UserSignUpState.SUCCESS)
            } else {
                _userSignUpState.postValue(UserSignUpState.FAILED)
            }
        }
    }

    fun clear() {
        _userSignUpState.value = UserSignUpState.DEFAULT
    }
}