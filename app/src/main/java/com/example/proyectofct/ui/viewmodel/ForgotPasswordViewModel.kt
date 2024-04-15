package com.example.proyectofct.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.proyectofct.domain.ForgotPasswordUseCase
import com.example.proyectofct.domain.SignUpUseCase
import com.google.firebase.auth.FirebaseAuth

class ForgotPasswordViewModel:ViewModel() {
    private val firebaseAuth = FirebaseAuth.getInstance()
    private val fgUseCase = ForgotPasswordUseCase(firebaseAuth)

    private val _fgResult = MutableLiveData<Pair<Boolean, String?>>()
    val forgotPasswordResult: LiveData<Pair<Boolean, String?>>
        get() = _fgResult

    fun sendEmail(email: String) {
        fgUseCase.sendEmail(email) { success, errorMessage ->
            _fgResult.postValue(Pair(success, errorMessage))
        }
    }
}