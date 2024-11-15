package com.pedro.criptocoin.presentation.coin_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pedro.criptocoin.data.remote.api.RetrofitInstance
import com.pedro.criptocoin.data.repository.CoinRepositoryImpl
import com.pedro.criptocoin.domain.model.Coin
import com.pedro.criptocoin.domain.model.CoinDetail
import com.pedro.criptocoin.domain.use_case.GetCoinDetailUseCase
import com.pedro.criptocoin.domain.use_case.GetCoinsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class CoinListViewModel : ViewModel() {
    private val api = RetrofitInstance.api
    private val repository = CoinRepositoryImpl(api)
    private val getCoinsUseCase = GetCoinsUseCase(repository)

    val coins = MutableStateFlow<List<Coin>>(emptyList())

    fun fetchCoins() {
        viewModelScope.launch {
            try {
                coins.value = getCoinsUseCase()
            } catch (e: Exception) {
                coins.value = emptyList()
            }
        }
    }
}

class CoinDetailViewModel : ViewModel() {
    private val api = RetrofitInstance.api
    private val repository = CoinRepositoryImpl(api)
    private val getCoinDetailUseCase = GetCoinDetailUseCase(repository)

    val coinDetail = MutableStateFlow<CoinDetail?>(null)

    fun fetchCoins(coinId: String) {
        viewModelScope.launch {
            try {
                coinDetail.value = getCoinDetailUseCase(coinId)
            } catch (e: Exception) {
                coinDetail.value = null
            }
        }
    }
}