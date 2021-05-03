package com.bcp.androidchallenge.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

@BindingAdapter("loadImage")
fun ImageView.loadImage(url: Int) {

    Glide.with(context)
        .load(url)
        .into(this)

}


@BindingAdapter("imageUrl", "imageCornerRadius")
fun ImageView.loadImageCorner(url: String, corner: Int) {

    Glide.with(context)
            .load(url)
            .transform(CenterCrop(), RoundedCorners(corner))
            .into(this)

}