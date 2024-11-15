package com.pedro.criptocoin.presentation.coin_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pedro.criptocoin.domain.model.Coin

@Composable
fun CoinListScreen(
    viewModel: CoinListViewModel = viewModel,
    onCoinSelected: (String) -> Unit
) {
    val coinList = viewModel.coinList.collectAsState(initial = emptyList())

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(coinList.value) { coin ->
            CoinListItem(coin = coin, onCoinClick = { onCoinSelected(coin.id) })
        }
    }
}

@Composable
fun CoinListItem(coin: Coin, onCoinClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onCoinClick() }
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = coin.name, modifier = Modifier.weight(1f))
        Text(text = coin.symbol)
    }
}
