package com.example.cryptoapp.pojo

import com.google.gson.JsonObject
import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


data class CoinPriceInfoInfoRawData(
    @SerializedName("RAW")
    @Expose
    val coinPriceInfoObject: JsonObject? = null
)
