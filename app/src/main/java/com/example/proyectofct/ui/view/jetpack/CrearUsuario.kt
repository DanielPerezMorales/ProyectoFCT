package com.example.proyectofct.ui.view.jetpack

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import com.example.proyectofct.core.Alert
import com.example.proyectofct.ui.viewmodel.SignUpViewModel
import com.google.firebase.auth.FirebaseAuth

private val firebaseAuth= FirebaseAuth.getInstance()
private val viewModel = SignUpViewModel(firebaseAuth)
private val alert= Alert()
@Composable
fun RegistroIberdrola(navController: NavController?, context: Context?) {
    BodyRegistro(navController, context)
}

@Composable
fun EditTextsRegistro(navController: NavController?, context: Context?) {
    var textEmail by remember { mutableStateOf("") }
    var textPassword by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .padding(20.dp)
    ) {
        EditText(textEmail, "Ingrese Email") { newText ->
            textEmail = newText
        }
        Spacer(modifier = Modifier.height(10.dp))
        EditText(textPassword, "Ingrese contraseña") { newText ->
            textPassword = newText
        }
        Spacer(modifier = Modifier.height(10.dp))
        Spacer(modifier = Modifier.height(160.dp))
        Button( textEmail, textPassword, navController, LocalContext.current)
        Spacer(modifier = Modifier.height(5.dp))
        Linea("Si ya tienes cuenta")
        Spacer(modifier = Modifier.height(5.dp))
        ButtonSecundario("Ir a inicio de sesion",navController, "login")
    }
}

@Composable
private fun Button(
    email: String?,
    password: String?,
    navController: NavController?, context: Context
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 50.dp),
        verticalArrangement = Arrangement.Center,
    ) {
        Button(
            onClick = {
                if (email != null && password != null) {
                    viewModel.signUp(email,password)

                    viewModel.signupResult.observe(context as LifecycleOwner) { result ->
                        val (success, errorMessage) = result
                        if (success) {
                            navController?.navigate("menu_principal")
                        } else {
                            alert.showAlert(
                                "Error",
                                errorMessage ?: "Error desconocido al crear usuario.",
                                context
                            )
                        }
                    }
                }
            },
            modifier = Modifier
                .width(250.dp)
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF9CBB29))
        ) {
            Text(text = "Entrar")
        }
    }
}

@Composable
fun BodyRegistro(navController: NavController?, context:Context?) {
    Column(Modifier.background(Color.White)) {
        LogoIberdrola()
        EditTextsRegistro(navController, context)
    }
}

@Preview(showSystemUi = true)
@Composable
fun PreviewRegistro(){
    RegistroIberdrola(null, null)
}