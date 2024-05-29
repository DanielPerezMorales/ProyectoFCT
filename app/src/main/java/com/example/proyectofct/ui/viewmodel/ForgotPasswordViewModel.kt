package com.example.proyectofct.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.proyectofct.domain.ForgotPasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(var fgUseCase: ForgotPasswordUseCase):ViewModel() {
    private val _fgResult = MutableLiveData<Pair<Boolean, String?>>()
    val forgotPasswordResult: LiveData<Pair<Boolean, String?>>
        get() = _fgResult

    fun sendEmail(email: String) {
        fgUseCase.sendEmail(email) { success, errorMessage ->
            _fgResult.postValue(Pair(success, errorMessage))
        }
    }
}