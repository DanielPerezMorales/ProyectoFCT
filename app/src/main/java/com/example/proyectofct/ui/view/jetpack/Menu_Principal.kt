package com.example.proyectofct.ui.view.jetpack

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.proyectofct.R
import com.example.proyectofct.core.Alert
import com.google.firebase.auth.FirebaseAuth

private val alert = Alert()


@Composable
fun Menu_principal(navController: NavController?, email: String?, pass: String?, check: Boolean?) {
    Body_menu(navController)
    if (check == true) {
        val prefs = LocalContext.current.getSharedPreferences(
            LocalContext.current.getString(R.string.sheredPrefJetpack),
            Context.MODE_PRIVATE
        ).edit()
        prefs.clear()
        prefs.putString("email", email)
        prefs.putString("password", pass)
        prefs.putBoolean("check", check)
        prefs.apply()
    }
}

@Composable
fun Body_menu(navController: NavController?) {
    Column(Modifier.background(Color.White)) {
        var isActive by remember { mutableStateOf(false) }
        val context = LocalContext.current
        Titulo()
        Spacer(modifier = Modifier.height(20.dp))
        TextWithButton("Práctica 1") {
            navController?.navigate("practica1$isActive")
        }
        TextWithButton("Práctica 2") {
            navController?.navigate("SS")
        }
        TextWithButton("Navegación") {
            navController?.navigate("navegador")
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 10.dp)
            ) {
                Text(
                    text = "Mock",
                    fontSize = 20.sp,
                    modifier = Modifier.padding(10.dp)
                )
            }

            Switch(checked = isActive, onCheckedChange = {
                isActive = it
                if (isActive) {
                    Toast.makeText(context, "Mock activado", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Mock desactivado", Toast.LENGTH_SHORT).show()
                }
            }, Modifier.padding(16.dp))
        }

        Divider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 1.dp
        )
        Spacer(modifier = Modifier.height(200.dp))
        TextWithButtonSignOut {
            val prefs =
                context.getSharedPreferences(
                    context.getString(R.string.sheredPrefJetpack),
                    Context.MODE_PRIVATE
                ).edit()
            prefs.clear()
            prefs.apply()
            FirebaseAuth.getInstance().signOut()
            navController?.navigate("login")
        }
    }
}

@Composable
fun Titulo() {
    Text(text = "Prácticas Android", Modifier.padding(top = 60.dp, start = 15.dp), fontSize = 30.sp)
}

@Composable
fun TextWithButton(text: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 10.dp)
        ) {
            Text(
                text = text,
                fontSize = 20.sp,
                modifier = Modifier.padding(10.dp)
            )
        }

        IconButton(
            onClick = onClick,
            modifier = Modifier.padding(16.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_arrow_forward_ios_24),
                contentDescription = "Forward Icon"
            )
        }
    }

    Divider(
        modifier = Modifier.fillMaxWidth(),
        thickness = 1.dp
    )
}

@Composable
private fun TextWithButtonSignOut(onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 10.dp)
        ) {
            Text(
                text = stringResource(id = R.string.cerrar_sesi_n),
                fontSize = 20.sp,
                modifier = Modifier.padding(10.dp)
            )
        }

        IconButton(
            onClick = onClick,
            modifier = Modifier.padding(16.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_exit_to_app_24),
                contentDescription = "Sign Out Icon"
            )
        }
    }

    Divider(
        modifier = Modifier.fillMaxWidth(),
        thickness = 1.dp
    )
}

@Preview(showSystemUi = true)
@Composable
fun Preview_menu() {
    Menu_principal(navController = null, email = "null", pass = "pass", check = false)
}