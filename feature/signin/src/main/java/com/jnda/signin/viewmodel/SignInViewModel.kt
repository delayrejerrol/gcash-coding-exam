package com.jnda.signin.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jnda.common.viewmodel.BaseViewModel
import com.jnda.signin.data.repository.SignInRepository
import com.jnda.signin.state.UserSignInState
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class SignInViewModel(
    private val signInRepository: SignInRepository
) : BaseViewModel() {

    private val _userSignInState: MutableLiveData<UserSignInState> by lazy { MutableLiveData() }
    val userSignInState: LiveData<UserSignInState> = _userSignInState

    fun signIn(username: String, password: String) {
        signInRepository.findByAccount(username, password).also { user ->
            if (user != null) {
                _userSignInState.postValue(UserSignInState.SUCCESS)
            } else {
                _userSignInState.postValue(UserSignInState.FAILED)
            }
        }
    }

    fun clear() {
        _userSignInState.value = UserSignInState.DEFAULT
    }
}