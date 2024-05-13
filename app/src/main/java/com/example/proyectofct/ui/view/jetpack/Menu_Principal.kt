package com.example.proyectofct.ui.view.jetpack

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.proyectofct.R

@Composable
fun Menu_principal(navController: NavController?) {
    Body_menu(navController)
}

@Composable
fun Body_menu(navController: NavController?) {
    Column(Modifier.background(Color.White)) {
        Titulo()
        Spacer(modifier = Modifier.height(20.dp))
        TextWithButton("Práctica 1") {
            navController?.navigate("practica1")
        }
        TextWithButton("Práctica 2") {
            navController?.navigate("second_screenSMART_SOLAR")
        }
        TextWithButton("Navegación") {
            navController?.navigate("second_screenNAVEGACION")
        }
        TextWithSwitch("Mock")
    }
}

@Composable
fun Titulo() {
    Text(text = "Prácticas Android", Modifier.padding(top = 60.dp, start = 15.dp), fontSize = 30.sp)
}

@Composable
fun TextWithButton(text:String, onClick: () -> Unit) {
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
            onClick =  onClick,
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
fun TextWithSwitch(text:String) {
    var isActive by remember { mutableStateOf(false) }
    val context = LocalContext.current
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
}


@Preview(showSystemUi = true)
@Composable
fun Preview_menu() {
    Menu_principal(navController = null)
}