package com.example.cryptoapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptoapp.R
import com.example.cryptoapp.pojo.CoinPriceInfo
import com.squareup.picasso.Picasso

class CoinInfoAdapter(
    private val context: Context
) : RecyclerView.Adapter<CoinInfoAdapter.ViewHolder>() {

    var coinPriceInfoList: List<CoinPriceInfo> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var onCoinClickListener: OnCoinClickListener? = null

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val coinCurrencyTextView: TextView = view.findViewById(R.id.coinCurrencyTextView)
        val priceTextView: TextView = view.findViewById(R.id.priceTextView)
        val updatedTimeTextView: TextView = view.findViewById(R.id.updatedTimeTextView)
        val iconImageView: ImageView = view.findViewById(R.id.iconImageView)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_coin_info, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val coinPriceInfo = coinPriceInfoList[position]

        viewHolder.coinCurrencyTextView.text =
            String.format(
                context.getString(R.string.coin_currency),
                coinPriceInfo.fromSymbol,
                coinPriceInfo.toSymbol
            )
        viewHolder.priceTextView.text = coinPriceInfo.price
        viewHolder.updatedTimeTextView.text =
            String.format(
                context.getString(R.string.last_updated_time),
                coinPriceInfo.getFormattedTime()
            )
        Picasso.get().load(coinPriceInfo.getIconUrl()).into(viewHolder.iconImageView)

        viewHolder.itemView.setOnClickListener {
            onCoinClickListener?.onCoinClick(coinPriceInfo)
        }
    }

    override fun getItemCount(): Int = coinPriceInfoList.size

    interface OnCoinClickListener {
        fun onCoinClick(coinPriceInfo: CoinPriceInfo)
    }
}