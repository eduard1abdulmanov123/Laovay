package com.abdulmanov.laovay.mvp.model.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Word(
    val id:Int,
    val word:String,
    val translate:String,
    val transcription:String?
):Parcelable