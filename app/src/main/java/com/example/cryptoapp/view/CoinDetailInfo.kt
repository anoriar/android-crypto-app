package com.example.cryptoapp.view

import android.R
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso


data class CoinDetailInfo(
    val fSym: String,
    val tSym: String?,
    val price: String?,
    val minPerDay: String?,
    val maxPerDay: String?,
    val lastDeal: String?,
    val updated: String?,
    val imageUrl: String? = null
) {

    companion object {
        @BindingAdapter("imageUrl")
        @JvmStatic
        fun loadImage(view: ImageView, imageUrl: String?) {
            Picasso.get()
                .load(imageUrl)
                .into(view)
        }
    }

}