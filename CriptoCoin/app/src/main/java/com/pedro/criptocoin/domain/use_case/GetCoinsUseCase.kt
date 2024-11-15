package com.pedro.criptocoin.domain.use_case

import com.pedro.criptocoin.domain.model.Coin
import com.pedro.criptocoin.domain.repository.CoinRepository

class GetCoinsUseCase(private val repository: CoinRepository) {
    suspend operator fun invoke(): List<Coin> {
        return repository.getCoins()
    }
}