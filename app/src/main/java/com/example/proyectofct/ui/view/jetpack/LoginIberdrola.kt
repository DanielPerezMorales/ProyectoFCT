package com.example.proyectofct.ui.view.jetpack

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Checkbox
import androidx.compose.material.Divider
import androidx.compose.material.TextField
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import com.example.proyectofct.R
import com.example.proyectofct.core.Alert
import com.example.proyectofct.ui.viewmodel.LoginViewModel
import com.google.firebase.auth.FirebaseAuth

private val firebaseAuth = FirebaseAuth.getInstance()
private val viewModel = LoginViewModel(firebaseAuth)
private val alert = Alert()
private var isCheck = false

@Composable
fun LoginIberdrola(navController: NavController, context: Context) {
    val prefs = LocalContext.current.getSharedPreferences(
        LocalContext.current.getString(R.string.sheredPrefJetpack),
        Context.MODE_PRIVATE
    )
    val email = prefs.getString("email", null)
    val password = prefs.getString("password", null)
    if (email != null && password != null) {
        login(email, password, navController, context)
    }
    Body(navController, context)
}

@Composable
fun Imagen() {
    androidx.compose.foundation.Image(
        painterResource(id = R.drawable.ic_logo_iberdrola_cli),
        contentDescription = "IMAGEN DE PRUEBA",
        Modifier
            .clip(CircleShape)
            .background(
                MaterialTheme.colorScheme.background
            )
            .size(150.dp)
    )
}

@Composable
fun LogoIberdrola() {
    Column(
        Modifier
            .background(MaterialTheme.colorScheme.background)
            .padding(40.dp)
            .fillMaxWidth(), verticalArrangement = Arrangement.Center, Alignment.CenterHorizontally
    ) {
        Imagen()
    }
}

@Composable
fun EditTexts(navController: NavController, context: Context) {
    var isChecked by remember { mutableStateOf(false) }
    var textEmail by remember { mutableStateOf("danieles.03@gmail.com") }
    var textPassword by remember { mutableStateOf("123456") }
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
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = isChecked, onCheckedChange = {
                isChecked = it
                isCheck = isChecked
            })
            Text(text = "Recordar contraseña")
        }
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "He olvidado mis datos",
            textDecoration = TextDecoration.Underline,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.clickable { navController.navigate("FG") }
        )
        Spacer(modifier = Modifier.height(100.dp))
        Button("Entrar", textEmail, textPassword, navController, context)
        Spacer(modifier = Modifier.height(5.dp))
        Linea("También puedes")
        Spacer(modifier = Modifier.height(5.dp))
        ButtonSecundario("Ir a registro", navController, "registro")
    }
}

@Composable
private fun Button(
    textto: String,
    email: String?,
    password: String?,
    navController: NavController?,
    context: Context
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
                    if (navController != null) {
                        login(email, password, navController, context)
                    }
                }
            },
            modifier = Modifier
                .width(250.dp)
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF9CBB29))
        ) {
            Text(text = textto)
        }
    }
}

private fun login(email: String, password: String, navController: NavController, context: Context) {
    viewModel.login(email, password)

    viewModel.loginResult.observe(context as LifecycleOwner, Observer { result ->
        val (success, errorMessage) = result
        if (success) {
            if (isCheck) {
                navController.navigate("menu_principal/${email}/${password}/true")
            } else {
                navController.navigate("menu_principal/${email}/${password}/false")
            }

        } else {
            alert.showAlert(
                "Error",
                errorMessage ?: "Error desconocido al iniciar sesión.",
                context
            )
        }
    })
}

@Composable
fun ButtonSecundario(texto: String, navController: NavController?, ruta: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 50.dp),
        verticalArrangement = Arrangement.Center,
    ) {
        Button(
            onClick = {
                navController?.navigate(ruta)
            },
            modifier = Modifier
                .width(250.dp)
                .clip(shape = MaterialTheme.shapes.extraLarge)
                .height(50.dp)
                .border(
                    width = 5.dp,
                    color = Color.Gray
                ),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Gray,
                contentColor = Color.White
            )
        ) {
            Text(text = texto)
        }
    }
}

@Composable
fun EditText(texto: String, text: String, onTextChange: (String) -> Unit) {
    TextField(
        value = texto,
        onValueChange = onTextChange,
        label = { Text(text) },
        singleLine = true,
        modifier = Modifier.fillMaxWidth()
    )

    Spacer(modifier = Modifier.height(16.dp))
}

@Composable
fun Body(navController: NavController, context: Context) {
    Column(Modifier.background(Color.White)) {
        LogoIberdrola()
        EditTexts(navController, context)
    }
}

@Composable
fun Linea(text: String) {
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
            text = text,
            Modifier
                .background(Color.White)
                .width(150.dp)
                .padding(start = 22.dp)
        )
    }
}

