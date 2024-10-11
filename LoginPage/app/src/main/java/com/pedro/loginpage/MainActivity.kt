package com.pedro.loginpage

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LoginPage()
        }
    }
}

@Composable
fun LoginPage() {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var welcomeMessageVisible by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Welcome")
            Spacer(modifier = Modifier.height(24.dp))

            TextField(
                value = email,
                label = { Text(text = "Email") },
                onValueChange = { email = it }
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = password,
                label = { Text(text = "Password") },
                onValueChange = { password = it }
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    welcomeMessageVisible = true
                }
            ) {
                Text(text = "Login")
            }

            Spacer(modifier = Modifier.height(24.dp))

            if (welcomeMessageVisible) {
                //Text(text = "Seja Bem-Vindo!!!")
                Text(text = "Login Efetuado com Sucesso!")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginOnePreview() {
    LoginPage()
}