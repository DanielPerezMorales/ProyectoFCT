package com.example.proyectofct.ui.view.jetpack

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Divider
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.IconButton
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import com.example.proyectofct.R
import com.example.proyectofct.core.Alert
import com.example.proyectofct.ui.viewmodel.SignUpViewModel
import com.google.firebase.auth.FirebaseAuth

private val firebaseAuth= FirebaseAuth.getInstance()
//private val viewModel = SignUpViewModel(firebaseAuth)
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
        EditText(textEmail) { newText ->
            textEmail = newText
        }
        Spacer(modifier = Modifier.height(10.dp))
        EditTextWithEye(textPassword) { newText ->
            textPassword = newText
        }
        Spacer(modifier = Modifier.height(10.dp))
        Spacer(modifier = Modifier.height(160.dp))
        Button( textEmail, textPassword, navController, context!!)
        Spacer(modifier = Modifier.height(5.dp))
        Linea()
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
                    /*viewModel.signUp(email,password)

                    viewModel.signupResult.observe(context as LifecycleOwner) { result ->
                        val (success, errorMessage) = result
                        if (success) {
                            navController?.navigate("menu_principal/${email}/${password}/false")
                        } else {
                            alert.showAlert(
                                "Error",
                                errorMessage ?: "Error desconocido al crear usuario.",
                                context
                            )
                        }
                    }*/
                }
            },
            modifier = Modifier
                .width(250.dp)
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF9CBB29))
        ) {
            Text(text = "Registrar")
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

@Composable
private fun Linea() {
    Box(
        modifier = Modifier
            .width(500.dp)
            .height(20.dp),
        contentAlignment = Alignment.Center
    ) {
        Divider(
            modifier = Modifier.fillMaxWidth(),
            color = Color.Gray
        )
        Text(
            text = "Si ya tienes cuenta",
            Modifier
                .background(Color.White)
                .width(150.dp)
                .padding(start = 30.dp)
        )
    }
}
@Composable
private fun EditText(texto: String, onTextChange: (String) -> Unit) {
    TextField(
        value = texto,
        onValueChange = onTextChange,
        placeholder = { Text("Ingrese Email") },
        singleLine = true,
        modifier = Modifier.fillMaxWidth(),
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.White,
            textColor = Color.Black,
            disabledTextColor = Color.Black,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        )
    )

    Spacer(modifier = Modifier.height(16.dp))
}

@Composable
private fun EditTextWithEye(texto: String, onTextChange: (String) -> Unit) {
    var passwordVisible by remember { mutableStateOf(false) }
    TextField(
        value = texto,
        onValueChange = onTextChange,
        placeholder = { Text("Ingrese contraseña") },
        trailingIcon = {
            IconButton(
                onClick = {
                    passwordVisible = !passwordVisible
                }
            ) {
                val icon = if (passwordVisible) {
                    painterResource(id = R.drawable.ojo)
                } else {
                    painterResource(id = R.drawable.ojo)
                }
                Image(painter = icon, contentDescription = "Ver/ocultar contraseña")
            }
        },
        singleLine = true,
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        modifier = Modifier.fillMaxWidth(),
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.White,
            textColor = Color.Black,
            disabledTextColor = Color.Black,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        )
    )

    Spacer(modifier = Modifier.height(16.dp))
}

@Preview(showSystemUi = true)
@Composable
fun PreviewRegistro(){
    RegistroIberdrola(null, null)
}