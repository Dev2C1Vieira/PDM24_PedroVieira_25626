package com.pedro.ecommerce.ui.screens

import com.pedro.ecommerce.viewmodel.cart.CartViewModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.pedro.ecommerce.data.model.Product

@Composable
fun ProductScreen(
    products: List<Product>,
    cartViewModel: CartViewModel,
    navController: NavController // Adiciona o NavController como parâmetro
) {
    Column {
        // Botão para avançar para o Carrinho
        Button(
            onClick = { navController.navigate("cart") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(text = "Ir para o Carrinho")
        }

        // Lista de produtos
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(products.size) { index ->
                val product = products[index]
                ProductItem(
                    product = product,
                    onAddToCart = { cartViewModel.addToCart(product) }
                )
            }
        }
    }
}

@Composable
fun ProductItem(product: Product, onAddToCart: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.elevatedCardElevation(6.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Imagem do produto
            Image(
                painter = rememberAsyncImagePainter(model = product.imageUrl),
                contentDescription = null,
                modifier = Modifier
                    .size(100.dp) // Tamanho da imagem
                    .clip(RoundedCornerShape(12.dp)) // Bordas arredondadas
            )
            // Informações do produto
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = product.name,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = product.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "Preço: €${product.price}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "Categoria: ${product.category}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        }

        // Botão na parte inferior do Card
        Button(
            onClick = { onAddToCart() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .align(Alignment.CenterHorizontally),
            shape = RoundedCornerShape(8.dp),
        ) {
            Text("Adicionar ao Carrinho")
        }
    }
}