package com.example.proyectofct.ui.view.jetpack

import android.content.Context
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
import androidx.compose.material.TextField
//noinspection UsingMaterialAndMaterial3Libraries
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import com.example.proyectofct.core.Alert
import com.example.proyectofct.ui.viewmodel.ForgotPasswordViewModel

private val alert = Alert()

@Composable
fun FGIberdrola(
    navController: NavController?,
    context: Context
) {
    val viewModel: ForgotPasswordViewModel = hiltViewModel()
    BodyFG(navController, context, viewModel)
}

@Composable
fun EditTextsFG(
    navController: NavController?,
    context: Context,
    viewModel: ForgotPasswordViewModel
) {
    var textEmail by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .padding(20.dp)
    ) {
        EditText(textEmail) { newText ->
            textEmail = newText
        }
        Spacer(modifier = Modifier.height(230.dp))
        Button(email = textEmail, navController = navController, context, viewModel)
        Linea()
        Spacer(modifier = Modifier.height(5.dp))
        ButtonSecundario("Ir a inicio de sesion", navController, "login")
    }
}

@Composable
private fun Button(
    email: String?,
    navController: NavController?,
    context: Context, viewModel: ForgotPasswordViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 50.dp),
        verticalArrangement = Arrangement.Center,
    ) {
        Button(
            onClick = {
                if (email != null) {
                    sendEmail(navController, context, email, viewModel)
                }
            },
            modifier = Modifier
                .width(250.dp)
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF9CBB29))
        ) {
            Text(text = "Enviar email")
        }
    }
}

@Composable
fun BodyFG(navController: NavController?, context: Context, viewModel: ForgotPasswordViewModel) {
    Column(Modifier.background(Color.White)) {
        LogoIberdrola()
        EditTextsFG(navController, context, viewModel)
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
            text = "También puedes",
            Modifier
                .background(Color.White)
                .width(150.dp)
                .padding(start = 10.dp)
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

@Preview(showSystemUi = true)
@Composable
fun PreviewFG() {
    //FGIberdrola(null)
}

private fun sendEmail(
    navController: NavController?,
    context: Context,
    email: String,
    viewModel: ForgotPasswordViewModel
) {
    viewModel.sendEmail(email)

    viewModel.forgotPasswordResult.observe(context as LifecycleOwner) { result ->
        val (success, errorMessage) = result
        if (success) {
            navController?.navigate("login")
        } else {
            alert.showAlert(
                "Error",
                errorMessage ?: "Error desconocido al enviar el email.",
                context
            )
        }
    }
}