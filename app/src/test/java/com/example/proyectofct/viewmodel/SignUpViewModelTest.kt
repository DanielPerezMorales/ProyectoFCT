package com.example.proyectofct.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.proyectofct.domain.SignUpUseCase
import com.example.proyectofct.ui.viewmodel.SignUpViewModel
import com.google.firebase.auth.FirebaseAuth
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.Mockito.verify

class SignUpViewModelTest{
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    // Mock objects
    @Mock
    private lateinit var signUpUseCase: SignUpUseCase

    @Mock
    private lateinit var firebaseAuth: FirebaseAuth

    @Mock
    private lateinit var observer: Observer<Pair<Boolean, String?>>

    private lateinit var viewModel: SignUpViewModel

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
        viewModel = SignUpViewModel(firebaseAuth)
        viewModel.signUpUseCase = signUpUseCase
        viewModel.signupResult.observeForever(observer)
    }

    @Test
    fun `signUp success`() {
        // Given
        val email = "test@example.com"
        val password = "password"
        val success = true
        val errorMessage = null

        // When
        viewModel.signUp(email, password)

        // Then
        Thread.sleep(100)
        observer.onChanged(Pair(success, errorMessage))
        verify(observer).onChanged(Pair(success, errorMessage))
    }


    @Test
    fun `signUp failure`() {
        // Given
        val email = "test@example.com"
        val password = "password"
        val success = false
        val errorMessage = "Invalid credentials"

        // When
        viewModel.signUp(email, password)

        // Then
        observer.onChanged(Pair(success, errorMessage))
    }
}