package com.example.proyectofct.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.proyectofct.domain.LoginUseCase
import com.example.proyectofct.ui.viewmodel.LoginViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class LoginViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    // Mock objects
    @Mock
    private lateinit var loginUseCase: LoginUseCase

    @Mock
    private lateinit var observer: Observer<Pair<Boolean, String?>?>

    private lateinit var viewModel: LoginViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel = LoginViewModel(loginUseCase)
        viewModel.loginUseCase = loginUseCase
        viewModel.loginResult.observeForever(observer)
    }

    @Test
    fun `login success`() {
        // Given
        val email = "test@example.com"
        val password = "password"
        val success = true
        val errorMessage = null

        // When
        viewModel.login(email, password)

        // Then
        observer.onChanged(Pair(success, errorMessage))
    }


    @Test
    fun `login failure`() {
        // Given
        val email = "test@example.com"
        val password = "password"
        val success = false
        val errorMessage = "Invalid credentials"

        // When
        viewModel.login(email, password)

        // Then
        observer.onChanged(Pair(success, errorMessage))
    }
}
