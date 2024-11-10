package com.pedro.carmuseum.ui.theme.screens

import CarViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun CarDetailScreen(navController: NavController, carId: String?) {
    // Obter o ViewModel diretamente na função Composable
    val viewModel: CarViewModel = viewModel()

    // Obter o carro a partir do ViewModel usando o ID
    val car = carId?.let { id -> viewModel.getCarById(id.toInt()) }

    if (car != null) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(text = "Detalhes do Carro", style = MaterialTheme.typography.headlineSmall, modifier = Modifier.padding(bottom = 16.dp))
            Text(text = "Nome: ${car.name}", style = MaterialTheme.typography.bodyLarge)
            Text(text = "Marca: ${car.brand}", style = MaterialTheme.typography.bodyLarge)
            Text(text = "Modelo: ${car.model}", style = MaterialTheme.typography.bodyLarge)
            Text(text = "Ano: ${car.year}", style = MaterialTheme.typography.bodyLarge)

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { navController.popBackStack() },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(text = "Voltar")
            }
        }
    } else {
        Text(
            text = "Carro não encontrado.",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center)
        )
    }
}
