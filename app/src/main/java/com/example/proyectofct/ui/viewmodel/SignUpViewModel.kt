package com.example.proyectofct.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.proyectofct.domain.SignUpUseCase
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(var signUpUseCase: SignUpUseCase) :ViewModel() {
    private val _signUpResult = MutableLiveData<Pair<Boolean, String?>>()
    val signupResult: LiveData<Pair<Boolean, String?>>
        get() = _signUpResult

    fun signUp(email: String, password: String) {
        signUpUseCase.login(email, password) { success, errorMessage ->
            _signUpResult.postValue(Pair(success, errorMessage))
        }
    }
}