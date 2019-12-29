package com.abdulmanov.laovay.mvp.model.core.favorites.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "viewed_words")
data class ViewedWord(
    @PrimaryKey
    val id:Int,
    val word:String,
    val translate:String,
    val transcription:String?
)