package com.pedro.criptocoin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.pedro.criptocoin.presentation.coin_list.CoinDetailViewModel
import com.pedro.criptocoin.presentation.coin_list.CoinListScreen
import com.pedro.criptocoin.presentation.coin_list.CoinListViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen()
        }
    }
}

@Composable
fun MainScreen() {
    val selectedCoinId by remember { mutableStateOf<String?>(null) }

    if (selectedCoinId == null) {
        val coinListViewModel: CoinListViewModel = viewModel()
        CoinListScreen(coinListViewModel) {
                coinId -> selectedCoinId = coinId
        }
    } else {
        val coinDetailViewModel: CoinDetailViewModel = viewModel()
        CoinDetailListScreen(coinDetailViewModel, selectedCoinId!!) {
            selectedCoinId = null
        }
    }
}