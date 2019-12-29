package com.abdulmanov.laovay.common

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

fun ViewGroup.inflate(layoutId:Int, attachToRoot:Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutId, this, attachToRoot)
}

fun View.visibility(visible: Boolean) {
    visibility = if (visible) View.VISIBLE else View.GONE
}