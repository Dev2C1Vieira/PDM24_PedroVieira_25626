package com.pedro.criptocoin.data.remote.model

import com.pedro.criptocoin.domain.model.Coin

data class CoinDto (
    val id: String,
    val name: String,
    val symbol: String
) {
    fun toCoin(): Coin {
        return Coin(id = id, name = name, symbol = symbol)
    }
}