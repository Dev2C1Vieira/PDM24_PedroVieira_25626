package com.pedro.criptocoin.domain.use_case

import com.pedro.criptocoin.domain.model.CoinDetail
import com.pedro.criptocoin.domain.repository.CoinRepository

class GetCoinDetailUseCase (private val repository: CoinRepository) {
    suspend operator fun invoke(coinId: String): CoinDetail {
        return repository.getCoinDetail(coinId)
    }
}