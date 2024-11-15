package com.pedro.criptocoin.domain.repository

import com.pedro.criptocoin.domain.model.Coin
import com.pedro.criptocoin.domain.model.CoinDetail

interface CoinRepository {
    suspend fun getCoins(): List<Coin>
    suspend fun getCoinDetail(coinId: String): CoinDetail
}