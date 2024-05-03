package com.example.proyectofct.viewmodel

import com.example.proyectofct.domain.LoginUseCase
import com.example.proyectofct.ui.viewmodel.LoginViewModel
import com.google.firebase.auth.FirebaseAuth
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations

class LoginViewModelTest {

    @Mock
    private lateinit var firebaseAuth: FirebaseAuth

    @Mock
    private lateinit var loginUseCase: LoginUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }
/*
    @Test
    fun login_success() {
        val email = "test@example.com"
        val password = "password123"
        val viewModel = LoginViewModel()

        // Mock loginUseCase success
        Mockito.`when`(loginUseCase.login(email, password)).thenAnswer { invocation ->
            val callback = invocation.arguments[2] as (Boolean, String?) -> Unit
            callback.invoke(true, null)
            return@thenAnswer Unit
        }

        // Call login function
        viewModel.login(email, password)

        // Capture login result through callback
        var capturedSuccess: Boolean? = null
        var capturedErrorMessage: String? = null
        val callback = mock<(Boolean, String?) -> Unit>()
        viewModel.loginResult.observeForever { result ->
            capturedSuccess = result?.first
            capturedErrorMessage = result?.second
        }

        // Assert login result
        assertEquals(true, capturedSuccess)
        assertNull(capturedErrorMessage)
    }

    @Test
    fun login_failure() {
        val email = "test@example.com"
        val password = "wrongPassword"
        val errorMessage = "Invalid password"
        val viewModel = LoginViewModel()

        // Mock loginUseCase failure
        Mockito.`when`(loginUseCase.login(email, password)).thenAnswer { invocation ->
            val callback = invocation.arguments[2] as (Boolean, String?) -> Unit
            callback.invoke(false, errorMessage)
            return@thenAnswer Unit
        }

        // Call login function
        viewModel.login(email, password)

        // Capture login result through callback
        var capturedSuccess: Boolean? = null
        var capturedErrorMessage: String? = null
        val callback = mock<(Boolean, String?) -> Unit>()
        viewModel.loginResult.observeForever { result ->
            capturedSuccess = result?.first
            capturedErrorMessage = result?.second
        }

        // Assert login result
        assertEquals(false, capturedSuccess)
        assertEquals(errorMessage, capturedErrorMessage)
    }*/
}


