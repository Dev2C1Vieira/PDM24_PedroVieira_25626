package com.pedro.ecommerce.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.pedro.ecommerce.viewmodel.auth.AuthViewModel

/**
 * Tela de registo para o utilizador.
 * Permite que o utilizador insira email e senha para criar uma conta.
 */
@Composable
fun RegisterScreen(
    navController: NavController,
    authViewModel: AuthViewModel
) {
    // Variáveis para armazenar email e senha
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // Observa mensagens de erro
    val errorMessage by authViewModel.errorMessage.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        // Campo para inserir email
        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") }
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Campo para inserir senha
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Botão de registo
        Button(onClick = {
            authViewModel.register(email, password)
            // Voltar ao login após registar
            navController.navigate("login") {
                popUpTo("register") { inclusive = true }
            }
        }) {
            Text("Registar")
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Mensagem de erro
        errorMessage?.let {
            Text(text = it, color = MaterialTheme.colorScheme.error)
        }
    }
}