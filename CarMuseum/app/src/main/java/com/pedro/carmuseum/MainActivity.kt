package com.pedro.carmuseum

import CarViewModel
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.pedro.carmuseum.ui.theme.screens.AddCarScreen
import com.pedro.carmuseum.ui.theme.screens.CarDetailScreen
import com.pedro.carmuseum.ui.theme.screens.CarListScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            AppNavHost(navController)
        }
    }
}

@Composable
fun AppNavHost(navController: NavHostController) {
    val viewModel: CarViewModel = viewModel()

    NavHost(navController = navController, startDestination = "car_list") {
        composable("car_list") { CarListScreen(navController, viewModel) }

        composable("car_detail/{carId}") { backStackEntry ->
            val carId = backStackEntry.arguments?.getString("carId")
            CarDetailScreen(navController, carId)
        }

        composable("add_car") { AddCarScreen(navController) }
    }
}
