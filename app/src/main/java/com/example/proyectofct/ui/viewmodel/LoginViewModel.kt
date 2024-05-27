package com.example.proyectofct.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.proyectofct.domain.LoginUseCase
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    var loginUseCase: LoginUseCase
) : ViewModel() {
    private val _loginResult = MutableLiveData<Pair<Boolean, String?>?>()
    val loginResult: MutableLiveData<Pair<Boolean, String?>?>
        get() = _loginResult

    fun login(email: String, password: String) {
        loginUseCase.login(email, password) { success, errorMessage ->
            _loginResult.postValue(Pair(success, errorMessage))
        }
    }

    fun resetLoginResult() {
        _loginResult.postValue(null)
    }
}
