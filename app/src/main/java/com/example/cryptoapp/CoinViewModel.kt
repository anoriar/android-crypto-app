package com.example.cryptoapp

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.cryptoapp.api.ApiFactory
import com.example.cryptoapp.database.AppDatabase
import com.example.cryptoapp.pojo.CoinPriceInfo
import com.example.cryptoapp.pojo.CoinPriceInfoInfoRawData
import com.google.gson.Gson
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class CoinViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.getInstance(application)
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    val priceList = db.coinPriceInfoDao().getPriceList()

    init {
        loadData()
    }

    fun getDetailInfo(fSym: String): LiveData<CoinPriceInfo> {
        return db.coinPriceInfoDao().getPriceInfoAboutCoin(fSym)
    }

    private fun loadData() {
        val disposable = ApiFactory.apiService.getTopCoinsInfo()
//                Формируем данные в строку BTC,ETH,EOS - из топа криптовалют
            .map { it.data?.map { it.coinInfo?.name }?.joinToString(",") }
//                Запрос на получение подробной инфы о валютах: ?fsyms=BTC,ETH,EOS&tsyms=USD
            .flatMap { ApiFactory.apiService.getFullPriceList(fSyms = it) }
//                Преобразование полученного json  в список информации о каждой валюте
            .map { getPriceListFromRawData(it) }
            .delaySubscription(10, TimeUnit.SECONDS)
            .repeat()
            .retry()
            .subscribeOn(Schedulers.io())
            .subscribe({
//                Сохраняем полученные валюты в бд
                db.coinPriceInfoDao().insertList(it)
                Log.d("TEST_OF_LOADING_DATA", "Success: $it")
            }, {
                Log.d("TEST_OF_LOADING_DATA", "Failure: ${it.message}")
            })
        compositeDisposable.add(disposable)
    }

    private fun getPriceListFromRawData(coinPriceInfoInfoRawData: CoinPriceInfoInfoRawData): List<CoinPriceInfo> {
        val priceList: ArrayList<CoinPriceInfo> = ArrayList<CoinPriceInfo>()
        val jsonObject = coinPriceInfoInfoRawData.coinPriceInfoObject
        if (jsonObject == null) return priceList

        for (coinKey in jsonObject.keySet()) {
            val currencyJson = jsonObject.getAsJsonObject(coinKey)
            for (currencyKey in currencyJson.keySet()) {
                priceList.add(
                    Gson().fromJson(
                        currencyJson.getAsJsonObject(currencyKey),
                        CoinPriceInfo::class.java
                    )
                )
            }
        }
        return priceList
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}