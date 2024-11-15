package com.pedro.criptocoin.presentation.coin_detail_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pedro.criptocoin.data.remote.api.RetrofitInstance
import com.pedro.criptocoin.data.repository.CoinRepositoryImpl
import com.pedro.criptocoin.domain.model.CoinDetail
import com.pedro.criptocoin.domain.use_case.GetCoinDetailUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

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