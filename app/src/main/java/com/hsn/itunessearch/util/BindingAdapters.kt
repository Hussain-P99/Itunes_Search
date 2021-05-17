package com.hsn.itunessearch.util

import android.net.Uri
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.hsn.itunessearch.R


@BindingAdapter("loadImage")
fun loadImage(imgView: ImageView, url: String) {
    Glide.with(imgView.context)
        .load(Uri.parse(url))
        .placeholder(R.drawable.ic_hourglass_empty)
        .error(R.drawable.ic_broken_image)
        .into(imgView)
}