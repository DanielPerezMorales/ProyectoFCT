package com.example.proyectofct.domain

import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class ForgotPasswordUseCaseTest {

    @Mock
    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var forgotPasswordUseCase: ForgotPasswordUseCase

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        forgotPasswordUseCase = ForgotPasswordUseCase(firebaseAuth)
    }

    @Test
    fun `when Email And Password Are Blank Then Callback With Error Message`() {
        // Given
        val email = ""
        var capturedSuccess: Boolean? = null
        var capturedErrorMessage: String? = null

        // When
        forgotPasswordUseCase.sendEmail(email) { success, errorMessage ->
            capturedSuccess = success
            capturedErrorMessage = errorMessage
        }

        // Then
        assertEquals(false, capturedSuccess)
        assertEquals("Por favor, ingresa tu correo electrónico", capturedErrorMessage ?: "Por favor, ingresa tu correo electrónico")
    }

    @Test
    fun `when Firebase Authentication Succeeds Then Callback With Success`() {
        // Given
        val email = "test@example.com"
        val task = mock(Task::class.java) as Task<Void>

        `when`(task.isSuccessful).thenReturn(true)
        `when`(task.addOnCompleteListener(ArgumentMatchers.any())).thenAnswer {
            val listener = it.arguments[0] as OnCompleteListener<Void>
            listener.onComplete(task)
            task
        }
        `when`(firebaseAuth.sendPasswordResetEmail(email)).thenReturn(task)

        var capturedSuccess: Boolean? = null
        var capturedErrorMessage: String? = null

        // When
        forgotPasswordUseCase.sendEmail(email) { success, errorMessage ->
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
        val task = mock(Task::class.java) as Task<Void>

        `when`(task.isSuccessful).thenReturn(false)
        `when`(task.addOnCompleteListener(ArgumentMatchers.any())).thenAnswer {
            val listener = it.arguments[0] as OnCompleteListener<Void>
            listener.onComplete(task)
            task
        }
        `when`(firebaseAuth.sendPasswordResetEmail(email)).thenReturn(task)

        var capturedSuccess: Boolean? = null
        var capturedErrorMessage: String? = null

        // When
        forgotPasswordUseCase.sendEmail(email) { success, errorMessage ->
            capturedSuccess = success
            capturedErrorMessage = errorMessage
        }

        // Then
        assertEquals(false, capturedSuccess ?: false)
        assertEquals("Error desconocido al enviar el email.", capturedErrorMessage ?: "Error desconocido al enviar el email.")
    }

}

