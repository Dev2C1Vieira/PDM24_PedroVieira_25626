package com.pedro.ecommerce.ui.screens

import com.pedro.ecommerce.viewmodel.cart.CartViewModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.pedro.ecommerce.data.model.CartItem

@Composable
fun CartScreen(
    cartViewModel: CartViewModel,
    navController: NavController,
    userId: String // ID do utilizador logado
) {
    // Carregar o carrinho do utilizador ao abrir o Screen
    LaunchedEffect(Unit) {
        cartViewModel.loadCart(userId)
    }

    val cartItems by cartViewModel.cartItems.collectAsState()
    val totalCartPrice by cartViewModel.totalCartPrice.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {

        // Verifica se o carrinho está vazio
        if (cartItems.isEmpty()) {
            // Exibe mensagem quando o carrinho está vazio
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "O carrinho está vazio!",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary
                )
                Button(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier.padding(top = 16.dp)
                ) {
                    Text("Voltar para Produtos")
                }
            }
        } else {
            Button(
                onClick = { navController.popBackStack() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Text(text = "Voltar para Produtos")
            }

            // Exibe os produtos no carrinho
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(cartItems.size) { index ->
                    val cartItem = cartItems[index]
                    CartItemBox(
                        cartItem = cartItem,
                        onIncreaseQuantity = { cartViewModel.increaseQuantity(cartItem.product) },
                        onDecreaseQuantity = { cartViewModel.decreaseQuantity(cartItem.product) },
                        onRemove = { cartViewModel.removeFromCart(cartItem.product) }
                    )
                }
            }

            // Exibe o preço total do carrinho
            Text(
                text = "Preço Total: €${"%.2f".format(totalCartPrice)}",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.CenterHorizontally),
                color = MaterialTheme.colorScheme.primary
            )

            // Botão para salvar o carrinho
            Button(
                onClick = { cartViewModel.saveCartToFirestore(userId) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text("Guardar Carrinho")
            }
        }
    }
}


@Composable
fun CartItemBox(
    cartItem: CartItem,
    onIncreaseQuantity: () -> Unit,
    onDecreaseQuantity: () -> Unit,
    onRemove: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .height(250.dp), // Altura total ajustada
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.elevatedCardElevation(6.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Primeira Row: Textos e Imagem
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.8f), // Ocupa 80% da altura
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Textos
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(0.5f)
                        .padding(8.dp),
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text(
                        text = cartItem.product.name,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "Preço Unitário: €${"%.2f".format(cartItem.product.price)}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = "Quantidade: ${cartItem.quantity}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = "Total: €${"%.2f".format(cartItem.totalPrice)}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                // Imagem
                Image(
                    painter = rememberAsyncImagePainter(cartItem.product.imageUrl),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(0.5f) // Ocupa 50% da largura
                        .padding(8.dp)
                        .clip(RoundedCornerShape(8.dp))
                )
            }

            // Segunda Row: Botões
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.2f) // Ocupa 20% da altura
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Botões de adicionar/diminuir
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Botão de aumentar quantidade
                    Button(
                        onClick = onIncreaseQuantity,
                        modifier = Modifier.size(50.dp), // Define o tamanho do botão
                        shape = RoundedCornerShape(12.dp), // Bordas arredondadas
                        contentPadding = PaddingValues(0.dp) // Remove padding adicional
                    ) {
                        Text(
                            text = "+",
                            fontSize = 20.sp, // Define o tamanho do texto
                            textAlign = TextAlign.Center, // Centraliza o texto
                            modifier = Modifier.fillMaxSize() // Garante que o texto ocupe o espaço do botão
                        )
                    }
                    // Botão de diminuir quantidade
                    Button(
                        onClick = onDecreaseQuantity,
                        modifier = Modifier.size(50.dp), // Define o tamanho do botão
                        shape = RoundedCornerShape(12.dp),
                        contentPadding = PaddingValues(0.dp) // Remove padding adicional
                    ) {
                        Text(
                            text = "-",
                            fontSize = 20.sp, // Define o tamanho do texto
                            textAlign = TextAlign.Center, // Centraliza o texto
                            modifier = Modifier.fillMaxSize() // Garante que o texto ocupe o espaço do botão
                        )
                    }
                }
                // Botão remover
                Button(
                    onClick = onRemove,
                    modifier = Modifier
                        .defaultMinSize(minWidth = 160.dp) // Largura mínima ajustada
                ) {
                    Text("Remover")
                }
            }
        }
    }
}