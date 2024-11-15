package com.pedro.criptocoin.data.remote.model

import com.pedro.criptocoin.domain.model.CoinDetail

data class CoinDetailDto (
    val id: String,
    val name: String,
    val description: String
) {
    fun toCoinDetail(): CoinDetail {
        return CoinDetail(id = id, name = name, description = description)
    }
}