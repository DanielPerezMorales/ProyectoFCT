package com.example.proyectofct.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.proyectofct.domain.LoginUseCase
import com.example.proyectofct.domain.SignUpUseCase
import com.google.firebase.auth.FirebaseAuth

class SignUpViewModel(firebaseAuth: FirebaseAuth):ViewModel() {
    var signUpUseCase = SignUpUseCase(firebaseAuth)

    private val _signUpResult = MutableLiveData<Pair<Boolean, String?>>()
    val signupResult: LiveData<Pair<Boolean, String?>>
        get() = _signUpResult

    fun signUp(email: String, password: String) {
        signUpUseCase.login(email, password) { success, errorMessage ->
            _signUpResult.postValue(Pair(success, errorMessage))
        }
    }
}