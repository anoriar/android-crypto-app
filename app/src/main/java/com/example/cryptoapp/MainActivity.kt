package com.example.cryptoapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptoapp.adapters.CoinInfoAdapter
import com.example.cryptoapp.pojo.CoinPriceInfo

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: CoinViewModel
    private lateinit var coinInfoAdapter: CoinInfoAdapter
    private lateinit var recyclerView: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initRecyclerView()

        viewModel = ViewModelProvider(this).get(CoinViewModel::class.java)

        viewModel.priceList.observe(this, Observer {
            coinInfoAdapter.coinPriceInfoList = it
        })

    }

    private fun initRecyclerView() {
        recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        coinInfoAdapter = CoinInfoAdapter(this)
        recyclerView.adapter = coinInfoAdapter
    }
}