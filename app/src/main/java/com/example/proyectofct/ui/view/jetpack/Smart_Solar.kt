package com.example.proyectofct.ui.view.jetpack

import android.content.Context
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.IconButton
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Tab
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.TabRow
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Text
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.proyectofct.ui.viewmodel.DetallesViewModel
import kotlinx.coroutines.launch

@Composable
fun SS_Pantalla(navController: NavController?, context: Context?) {
    val viewModel: DetallesViewModel = hiltViewModel()
    SS_Body(navController, context, viewModel)
}

@Composable
fun SS_Body(navController: NavController?, context: Context?, viewModel: DetallesViewModel) {
    Column(Modifier.background(color = colorResource(id = com.example.proyectofct.R.color.white))) {
        ParteDeArriba(navController)
        Spacer(modifier = Modifier.height(10.dp))
        TextoGrande()
        Spacer(modifier = Modifier.height(10.dp))
        TabScreen(context, viewModel)
    }
}

@Composable
private fun ParteDeArriba(navController: NavController?) {
    TopAppBar(
        backgroundColor = colorResource(id = com.example.proyectofct.R.color.transparente),
        elevation = 0.dp
    ) {
        IconButton(onClick = { navController?.popBackStack() }) {
            Image(
                painter = painterResource(id = com.example.proyectofct.R.drawable.baseline_arrow_back_ios_new_24),
                contentDescription = null
            )
        }
        Text(
            text = stringResource(id = com.example.proyectofct.R.string.SS_atras), fontSize = 20.sp,
            color = colorResource(
                id = com.example.proyectofct.R.color.color_consumo
            )
        )
    }
}

@Composable
private fun TextoGrande() {
    Text(
        text = stringResource(id = com.example.proyectofct.R.string.SS_smart_solar),
        fontSize = 30.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(start = 20.dp)
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun TabScreen(context: Context?, viewModel: DetallesViewModel) {
    val scope = rememberCoroutineScope()

    val pagerState = rememberPagerState(pageCount = { Tabs.entries.size })

    val selectedTabIndex = remember {
        derivedStateOf { pagerState.currentPage }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        TabRow(
            selectedTabIndex = selectedTabIndex.value,
            backgroundColor = colorResource(id = com.example.proyectofct.R.color.transparente)
        ) {
            Tabs.entries.forEachIndexed { index, currentTab ->
                Tab(
                    selected = selectedTabIndex.value == index,
                    selectedContentColor = colorResource(id = com.example.proyectofct.R.color.color_consumo),
                    unselectedContentColor = colorResource(id = com.example.proyectofct.R.color.black),
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(currentTab.ordinal)
                        }
                    },
                    text = { Text(text = currentTab.text) }
                )
            }
        }
        HorizontalPager(
            state = pagerState, modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                when (selectedTabIndex.value) {
                    0 -> Instalacion()
                    1 -> Energia()
                    2 -> context?.let { Detalles(it, viewModel) }
                }
            }
        }

    }
}


enum class Tabs(
    val text: String
) {
    Instalacion("Mi instalación"),
    Energia("Energía"),
    Detalles("Detalles")
}


@Preview(showSystemUi = true)
@Composable
fun Preview_SS() {
    //SS_Pantalla(null, null)
}