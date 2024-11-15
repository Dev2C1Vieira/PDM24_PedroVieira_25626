package com.pedro.criptocoin.data.repository

import com.pedro.criptocoin.data.remote.api.CoinPaprikaApi
import com.pedro.criptocoin.domain.model.Coin
import com.pedro.criptocoin.domain.model.CoinDetail
import com.pedro.criptocoin.domain.repository.CoinRepository

class CoinRepositoryImpl (private val api: CoinPaprikaApi): CoinRepository {
    override suspend fun getCoins(): List<Coin> {
        return api.getCoins().map { it.toCoin() }
    }

    override suspend fun getCoinDetail(coinId: String): CoinDetail {
        return api.getCoinDetail(coinId).toCoinDetail()
    }
}