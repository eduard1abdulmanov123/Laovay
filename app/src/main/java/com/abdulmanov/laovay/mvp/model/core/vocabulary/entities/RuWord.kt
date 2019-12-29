package com.abdulmanov.laovay.mvp.model.core.vocabulary.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Fts4
import androidx.room.PrimaryKey

@Entity(tableName = "fts_word_rus")
@Fts4
data class RuWord(
    @PrimaryKey
    @ColumnInfo(name = "rowid")
    val rowId:Int,
    val word:String,
    val translate:String
)