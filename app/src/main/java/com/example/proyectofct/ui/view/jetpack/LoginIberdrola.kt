package com.example.proyectofct.ui.view.jetpack

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
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
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Checkbox
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Divider
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.IconButton
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import com.example.proyectofct.R
import com.example.proyectofct.core.Alert
import com.example.proyectofct.ui.viewmodel.LoginViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

private val alert = Alert()
private var isCheck = false

@Composable
fun LoginIberdrola(navController: NavController, context: Context) {
    val viewModel: LoginViewModel = hiltViewModel()
    val prefs = LocalContext.current.getSharedPreferences(
        LocalContext.current.getString(R.string.sheredPrefJetpack),
        Context.MODE_PRIVATE
    )
    val date = prefs.getString("date", "1970-01-01")
    val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val currentDate = Calendar.getInstance()
    currentDate.set(Calendar.HOUR_OF_DAY, 0)
    currentDate.set(Calendar.MINUTE, 0)
    currentDate.set(Calendar.SECOND, 0)
    currentDate.set(Calendar.MILLISECOND, 0)
    val email = prefs.getString("email", null)
    val password = prefs.getString("password", null)
    val savedDate= formatter.parse(date!!)
    if (savedDate != null) {
        if (savedDate >= currentDate.time) {
            if (email != null && password != null) {
                login(email, password, navController, context, viewModel)
            }
        } else {
            Toast.makeText(context, "La sesión ha caducado", Toast.LENGTH_SHORT).show()
        }
    }

    Body(navController, context, viewModel)
}

@Composable
fun Imagen() {
    Image(
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
fun EditTexts(navController: NavController, context: Context, viewModel: LoginViewModel) {
    var isChecked by remember { mutableStateOf(false) }
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
        Button(textEmail, textPassword, navController, context, viewModel)
        Spacer(modifier = Modifier.height(5.dp))
        Linea()
        Spacer(modifier = Modifier.height(5.dp))
        ButtonSecundario("Ir a registro", navController, "registro")
    }
}

@Composable
private fun Button(
    email: String?,
    password: String?,
    navController: NavController?,
    context: Context, viewModel: LoginViewModel
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
                        login(email, password, navController, context, viewModel)
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

@SuppressLint("SimpleDateFormat")
private fun login(
    email: String,
    password: String,
    navController: NavController,
    context: Context,
    viewModel: LoginViewModel
) {
    viewModel.login(email, password)

    viewModel.loginResult.observe(context as LifecycleOwner) { result ->
        result?.let {
            val (success, errorMessage) = result
            if (success) {
                val formatter = SimpleDateFormat("yyyy-MM-dd")
                val date: String = formatter.format(Calendar.getInstance().time)
                if (isCheck) {
                    navController.navigate("menu_principal/${email}/${password}/true/${date}")
                } else {
                    navController.navigate("menu_principal/${email}/${password}/false/${date}")
                }

            } else {
                alert.showAlert(
                    "Error",
                    errorMessage ?: "Error desconocido al iniciar sesión.",
                    context
                )
            }
        }
    }
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
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                contentColor = colorResource(id = R.color.color_consumo),
            ),
            modifier = Modifier
                .width(250.dp)
                .height(50.dp)
                .clip(RectangleShape)
                .background(
                    color = colorResource(
                        id = R.color.transparente
                    )
                )
                .border(
                    shape = CircleShape,
                    color = colorResource(id = R.color.color_consumo),
                    width = 2.dp
                )
        ) {
            Text(text = texto)
        }
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
        modifier = Modifier.fillMaxWidth(), colors = TextFieldDefaults.textFieldColors(
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
fun Body(navController: NavController, context: Context, viewModel: LoginViewModel) {
    Column(Modifier.background(Color.White)) {
        LogoIberdrola()
        EditTexts(navController, context, viewModel)
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

