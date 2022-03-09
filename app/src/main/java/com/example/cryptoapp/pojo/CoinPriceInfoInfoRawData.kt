package com.example.cryptoapp.pojo

import com.google.gson.JsonObject
import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


class CoinPriceInfoInfoRawData(
    @SerializedName("RAW")
    @Expose
    private val coinPriceInfoObject: JsonObject? = null
)
