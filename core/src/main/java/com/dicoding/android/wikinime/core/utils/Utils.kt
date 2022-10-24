package com.dicoding.android.wikinime.core.utils

import android.widget.ImageView
import com.dicoding.android.wikinime.core.R
import com.bumptech.glide.Glide

fun ImageView.loadImage(url: String?) {
    Glide.with(this.context)
        .load(url)
        .centerCrop()
        .placeholder(R.drawable.poster_placeholder)
        .into(this)
}