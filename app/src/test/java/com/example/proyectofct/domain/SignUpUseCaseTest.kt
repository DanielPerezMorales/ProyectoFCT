package com.example.proyectofct.domain

import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class SignUpUseCaseTest {

    @Mock
    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var signUpUseCase: SignUpUseCase

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        signUpUseCase = SignUpUseCase(firebaseAuth)
    }

    @Test
    fun `when Email And Password Are Blank Then Callback With Error Message`() {
        // Given
        val email = ""
        val password = ""
        var capturedSuccess: Boolean? = null
        var capturedErrorMessage: String? = null

        // When
        signUpUseCase.login(email, password) { success, errorMessage ->
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
        val task = mock(Task::class.java) as Task<AuthResult>

        `when`(task.isSuccessful).thenReturn(true)
        `when`(task.addOnCompleteListener(any())).thenAnswer {
            val listener = it.arguments[0] as OnCompleteListener<AuthResult>
            listener.onComplete(task)
            task
        }
        `when`(firebaseAuth.createUserWithEmailAndPassword(email, password)).thenReturn(task)

        var capturedSuccess: Boolean? = null
        var capturedErrorMessage: String? = null

        // When
        signUpUseCase.login(email, password) { success, errorMessage ->
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
        val task = mock(Task::class.java) as Task<AuthResult>

        `when`(task.isSuccessful).thenReturn(false)
        `when`(task.addOnCompleteListener(any())).thenAnswer {
            val listener = it.arguments[0] as OnCompleteListener<AuthResult>
            listener.onComplete(task)
            task
        }
        `when`(firebaseAuth.createUserWithEmailAndPassword(email, password)).thenReturn(task)

        var capturedSuccess: Boolean? = null
        var capturedErrorMessage: String? = null

        // When
        signUpUseCase.login(email, password) { success, errorMessage ->
            capturedSuccess = success
            capturedErrorMessage = errorMessage
        }

        // Then
        assertEquals(false, capturedSuccess ?: false)
        assertEquals("Error desconocido al crear usuario.", capturedErrorMessage ?: "Error desconocido al crear usuario.")
    }

}

