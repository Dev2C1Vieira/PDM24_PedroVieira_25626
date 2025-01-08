package com.pedro.ecommerce.data.navigation

import com.pedro.ecommerce.viewmodel.cart.CartViewModel
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
import com.pedro.ecommerce.ui.screens.CartScreen
import com.pedro.ecommerce.viewmodel.auth.AuthViewModel
import com.pedro.ecommerce.viewmodel.auth.AuthViewModelFactory
import com.pedro.ecommerce.viewmodel.product.ProductViewModel
import com.pedro.ecommerce.viewmodel.product.ProductViewModelFactory

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    // Repositórios
    val authRepository = AuthRepository()
    val productRepository = ProductRepository()

    // ViewModels
    val authViewModel: AuthViewModel = viewModel(factory = AuthViewModelFactory(authRepository))
    val productViewModel: ProductViewModel = viewModel(factory = ProductViewModelFactory(productRepository))
    val cartViewModel: CartViewModel = viewModel() // Adiciona o com.pedro.ecommerce.viewmodel.cart.CartViewModel

    // Estados
    val products by productViewModel.productState.collectAsState()

    NavHost(navController = navController, startDestination = "login") {
        // Tela de Login/
        composable(route = "login") {
            LoginScreen(
                navController = navController,
                authViewModel = authViewModel
            )
        }
        // Tela de Registo
        composable(route = "register") {
            RegisterScreen(
                navController = navController,
                authViewModel = authViewModel
            )
        }
        // Tela de Produtos
        composable(route = "products") {
            ProductScreen(
                products = products,
                cartViewModel = cartViewModel,
                navController = navController,
                authViewModel = authViewModel
            )
        }
        // Tela de Carrinho
        composable(route = "cart") {
            val userId = authViewModel.getCurrentUser()?.uid ?: ""
            CartScreen(
                cartViewModel = cartViewModel,
                navController = navController,
                userId = userId // Passa o userId para o CartScreen
            )
        }
    }
}