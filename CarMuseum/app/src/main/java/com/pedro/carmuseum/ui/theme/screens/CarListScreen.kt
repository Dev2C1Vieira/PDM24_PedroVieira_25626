package com.pedro.carmuseum.ui.theme.screens

import CarViewModel
import android.annotation.SuppressLint
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.NavController
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CarListScreen(navController: NavController, viewModel: CarViewModel) {
    val cars by viewModel.allCars.observeAsState(emptyList())

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate("add_car") }) {
                Icon(Icons.Default.Add, contentDescription = "Adicionar Carro")
            }
        }
    ) {
        LazyColumn {
            items(cars) { car ->
                Text(
                    text = car.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { navController.navigate("car_detail/${car.id}") }
                        .padding(16.dp)
                )
            }
        }
    }
}
