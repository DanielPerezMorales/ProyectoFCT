package com.example.proyectofct.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.proyectofct.domain.LoginUseCase
import com.google.firebase.auth.FirebaseAuth

class LoginViewModel (firebaseAuth: FirebaseAuth): ViewModel() {
    var loginUseCase = LoginUseCase(firebaseAuth)
    private val _loginResult = MutableLiveData<Pair<Boolean, String?>>()
    val loginResult: LiveData<Pair<Boolean, String?>>
        get() = _loginResult

    fun login(email: String, password: String) {
        loginUseCase.login(email, password) { success, errorMessage ->
            _loginResult.postValue(Pair(success, errorMessage))
        }
    }
}
