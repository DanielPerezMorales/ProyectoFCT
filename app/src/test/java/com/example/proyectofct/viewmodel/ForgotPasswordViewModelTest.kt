package com.example.proyectofct.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.proyectofct.domain.ForgotPasswordUseCase
import com.example.proyectofct.domain.LoginUseCase
import com.example.proyectofct.ui.viewmodel.ForgotPasswordViewModel
import com.example.proyectofct.ui.viewmodel.LoginViewModel
import com.google.firebase.auth.FirebaseAuth
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class ForgotPasswordViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    // Mock objects
    @Mock
    private lateinit var forgotUseCase:ForgotPasswordUseCase

    @Mock
    private lateinit var firebaseAuth: FirebaseAuth

    @Mock
    private lateinit var observer: Observer<Pair<Boolean, String?>>

    private lateinit var viewModel: ForgotPasswordViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel = ForgotPasswordViewModel(forgotUseCase)
        viewModel.fgUseCase = forgotUseCase
        viewModel.forgotPasswordResult.observeForever(observer)
    }

    @Test
    fun `login success`() {
        // Given
        val email = "test@example.com"
        val success = true
        val errorMessage = null

        // When
        viewModel.sendEmail(email)

        // Then
        observer.onChanged(Pair(success, errorMessage))
    }


    @Test
    fun `login failure`() {
        // Given
        val email = "test@example.com"
        val success = false
        val errorMessage = "Invalid credentials"

        // When
        viewModel.sendEmail(email)

        // Then
        observer.onChanged(Pair(success, errorMessage))
    }
}
