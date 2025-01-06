package com.pedro.ecommerce.data.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.pedro.ecommerce.data.repository.AuthRepository
import com.pedro.ecommerce.data.repository.ProductRepository
import com.pedro.ecommerce.ui.screens.LoginScreen
import com.pedro.ecommerce.ui.screens.ProductScreen
import com.pedro.ecommerce.ui.screens.RegisterScreen
import com.pedro.ecommerce.viewmodel.auth.AuthViewModel
import com.pedro.ecommerce.viewmodel.auth.AuthViewModelFactory
import com.pedro.ecommerce.viewmodel.product.ProductViewModel
import com.pedro.ecommerce.viewmodel.product.ProductViewModelFactory

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val authRepository = AuthRepository()
    val authViewModel: AuthViewModel = viewModel(factory = AuthViewModelFactory(authRepository))
    val productRepository = ProductRepository()
    val productViewModel: ProductViewModel = viewModel(factory = ProductViewModelFactory(productRepository))

    NavHost(navController = navController, startDestination = "login") {
        composable(route = "login") {
            LoginScreen(
                navController = navController,
                authViewModel = authViewModel
            )
        }
        composable(route = "register") {
            RegisterScreen(
                navController = navController,
                authViewModel = authViewModel
            )
        }
        composable(route = "products") {
            val products by productViewModel.productState.collectAsState()
            ProductScreen(products = products)
        }
    }
}