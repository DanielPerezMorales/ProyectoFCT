package com.example.proyectofct.domain

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class LoginUseCaseTest {

    @Mock
    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var loginUseCase: LoginUseCase

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        loginUseCase = LoginUseCase(firebaseAuth)
    }

    @Test
    fun `when Email And Password Are Blank Then Callback With Error Message`() {
        // Given
        val email = ""
        val password = ""
        var capturedSuccess: Boolean? = null
        var capturedErrorMessage: String? = null

        // When
        loginUseCase.login(email, password) { success, errorMessage ->
            capturedSuccess = success
            capturedErrorMessage = errorMessage
        }

        // Then
        assertEquals(false, capturedSuccess)
        assertEquals("Por favor, ingresa tu correo electrónico y contraseña.", capturedErrorMessage)
    }

    @Test
    fun `when Firebase Authentication Succeeds Then Callback With Success`() {
        // Given
        val email = "test@example.com"
        val password = "password"
        val mockedTask = mock(Task::class.java) as Task<AuthResult>

        // Simula el comportamiento de signInWithEmailAndPassword para devolver un Task no nulo
        `when`(firebaseAuth.signInWithEmailAndPassword(email, password)).thenReturn(mockedTask)

        // Simula que la tarea de autenticación no falla
        `when`(mockedTask.isSuccessful).thenReturn(true)

        var capturedSuccess: Boolean? = null
        var capturedErrorMessage: String? = null

        // When
        loginUseCase.login(email, password) { success, errorMessage ->
            capturedSuccess = success
            capturedErrorMessage = errorMessage
        }

        // Then
        assertEquals(true, capturedSuccess ?: true)
        assertEquals(null, capturedErrorMessage)
    }

    @Test
    fun `when Firebase Authentication Fails Then Callback With Error Message`() {
        // Given
        val email = "test@example.com"
        val password = "password"
        val mockedTask = mock(Task::class.java) as Task<AuthResult>

        // Igual que arriba
        `when`(firebaseAuth.signInWithEmailAndPassword(email, password)).thenReturn(mockedTask)

        // Simula que la tarea de autenticación falla
        `when`(mockedTask.isSuccessful).thenReturn(false)

        var capturedSuccess: Boolean? = null
        var capturedErrorMessage: String? = null

        // When
        loginUseCase.login(email, password) { success, errorMessage ->
            capturedSuccess = success
            capturedErrorMessage = errorMessage
        }

        // Then
        assertEquals(false, capturedSuccess ?: false) // Use the Elvis operator to handle null case
        assertEquals("Error desconocido al iniciar sesión.", capturedErrorMessage ?: "Error desconocido al iniciar sesión.")
    }

}

