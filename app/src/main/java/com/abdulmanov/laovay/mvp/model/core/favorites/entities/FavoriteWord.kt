package com.abdulmanov.laovay.mvp.model.core.favorites.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_words")
data class FavoriteWord(
    @PrimaryKey
    val id:Int,
    val word:String,
    val translate:String,
    val transcription:String?
)