package com.example.cryptoapp.api

import com.example.cryptoapp.pojo.CoinInfoListOfData
import com.example.cryptoapp.pojo.CoinPriceInfoInfoRawData
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("top/totalvolfull")
    fun getTopCoinsInfo(
        @Query(PARAM_API_KEY) apiKey: String = API_KEY,
        @Query(PARAM_LIMIT) limit: Int = 10,
        @Query(PARAM_TSYM) tSym: String = CURRENCY
    ): Single<CoinInfoListOfData>

    @GET("pricemulti")
    fun getFullPriceList(
        @Query(PARAM_API_KEY) apiKey: String = API_KEY,
        @Query(PARAM_FSYM) fSym: String,
        @Query(PARAM_TSYM) tSym: String = CURRENCY
    )
    : Single<CoinPriceInfoInfoRawData>

    companion object {
        private const val PARAM_API_KEY = "api_key"
        private const val PARAM_LIMIT = "limit"
        private const val PARAM_TSYM = "tsym"
        private const val PARAM_FSYM = "fsym"

        private const val CURRENCY = "USD"
        private const val API_KEY = "38e31b46f63fd2611104580f2366eee0e108f5bf1683cd54090ff239bedb086b"

    }
}