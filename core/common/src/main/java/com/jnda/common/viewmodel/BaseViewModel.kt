package com.jnda.common.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

open class BaseViewModel : ViewModel() {
    protected fun makeRequest(unit : suspend () -> Unit) {
        viewModelScope.launch {
            try {
                unit.invoke()
            } catch (ex : Exception) {

            }
        }
    }
}