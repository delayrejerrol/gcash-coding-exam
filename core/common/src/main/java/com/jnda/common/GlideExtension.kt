package com.jnda.common

import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.loadImageUrl(url: String) {
    Glide.with(this.context)
        .load(url)
        .into(this)
}

fun ImageView.loadWeatherIcon(icon: String?) {
    Glide.with(this.context)
        .load("http://openweathermap.org/img/wn/$icon@2x.png")
        .into(this)
}