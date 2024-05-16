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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import com.example.proyectofct.core.Alert
import com.example.proyectofct.ui.viewmodel.ForgotPasswordViewModel
import com.google.firebase.auth.FirebaseAuth

private val firebaseAuth = FirebaseAuth.getInstance()
private val viewModel = ForgotPasswordViewModel(firebaseAuth)
private val alert = Alert()

@Composable
fun FGIberdrola(navController: NavController?, context: Context) {
    BodyFG(navController, context)
}

@Composable
fun EditTextsFG(navController: NavController?, context: Context) {
    var textEmail by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .padding(20.dp)
    ) {
        EditText(textEmail, "Ingrese Email") { newText ->
            textEmail = newText
        }
        Spacer(modifier = Modifier.height(230.dp))
        Button(email = textEmail, navController = navController, context)
        Linea("También puedes")
        Spacer(modifier = Modifier.height(5.dp))
        ButtonSecundario("Ir a inicio de sesion", navController, "login")
    }
}

@Composable
private fun Button(
    email: String?,
    navController: NavController?,
    context:Context
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
                    sendEmail(navController, context, email)
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
fun BodyFG(navController: NavController?, context: Context) {
    Column(Modifier.background(Color.White)) {
        LogoIberdrola()
        EditTextsFG(navController, context)
    }
}

@Preview(showSystemUi = true)
@Composable
fun PreviewFG() {
    //FGIberdrola(null)
}

private fun sendEmail(navController: NavController?, context: Context, email: String) {
    viewModel.sendEmail(email)

    viewModel.forgotPasswordResult.observe(context as LifecycleOwner, Observer { result ->
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
    })
}