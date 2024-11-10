package com.pedro.carmuseum.ui.theme.screens

import CarViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.pedro.carmuseum.data.Car

@Composable
fun AddCarScreen(navController: NavController, viewModel: CarViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    var name by remember { mutableStateOf("") }
    var brand by remember { mutableStateOf("") }
    var model by remember { mutableStateOf("") }
    var year by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "Adicionar Novo Carro", style = MaterialTheme.typography.headlineSmall, modifier = Modifier.padding(bottom = 16.dp))

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Nome") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = brand,
            onValueChange = { brand = it },
            label = { Text("Marca") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = model,
            onValueChange = { model = it },
            label = { Text("Modelo") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = year,
            onValueChange = { year = it },
            label = { Text("Ano") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
        )

        Button(
            onClick = {
                // Converte o campo de ano para Int e cria um novo objeto Car
                val newCar = Car(
                    name = name,
                    brand = brand,
                    model = model,
                    year = year.toIntOrNull() ?: 0
                )
                viewModel.insert(newCar)  // Adiciona o carro ao banco de dados
                navController.popBackStack()  // Volta para a tela anterior
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(text = "Salvar")
        }
    }
}
