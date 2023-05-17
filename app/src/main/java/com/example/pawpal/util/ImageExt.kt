package com.example.pawpal.util

import android.content.Context
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions

fun ImageView.loadImage(url: String) {
    Glide.with(this).load(url).fitCenter().centerCrop().apply(
        RequestOptions().transform(
            RoundedCorners(50)
        )
    )
        .into(this)
}

fun ImageView.loadImageSquare(url: String) {
    Glide.with(this).load(url).fitCenter().centerCrop().into(this)
}

fun ImageView.setDrawable(context: Context, drawable : Int) {
    this.setImageDrawable(
        ContextCompat.getDrawable(
            context,
            drawable
        )
    )
}



