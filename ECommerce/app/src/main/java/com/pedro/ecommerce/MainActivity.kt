package com.pedro.ecommerce

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.pedro.ecommerce.data.navigation.AppNavigation

/**
 * A atividade principal que configura o conteúdo da aplicação.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Chama a navegação da aplicação
            AppNavigation()
        }
    }
}