package com.example.cryptoapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.cryptoapp.databinding.ActivityDetailBinding
import com.example.cryptoapp.view.CoinDetailInfo
import com.squareup.picasso.Picasso

class DetailActivity : AppCompatActivity() {
    private lateinit var activityDetailBinding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val viewModel = ViewModelProvider(this).get(CoinViewModel::class.java)

        activityDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail)

        if (!intent.hasExtra(FSYM_INTENT_EXTRA)) {
            finish()
            return
        }

        viewModel.getDetailInfo(intent.getStringExtra(FSYM_INTENT_EXTRA).toString())
            .observe(this, Observer {

                val coinDetailInfo = CoinDetailInfo(
                    fSym = it.fromSymbol,
                    tSym = it.toSymbol,
                    price = it.price,
                    minPerDay = it.lowDay,
                    maxPerDay = it.highDay,
                    lastDeal = it.lastMarket,
                    updated = it.getFormattedTime(),
                    imageUrl = it.getIconUrl()
                )
                activityDetailBinding.coinDetailInfo = coinDetailInfo
            })
    }

    companion object {
        private const val FSYM_INTENT_EXTRA = "fSym"

        fun newIntent(context: Context, fSym: String): Intent {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(FSYM_INTENT_EXTRA, fSym)
            return intent
        }
    }
}