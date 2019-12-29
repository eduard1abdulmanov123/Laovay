package com.abdulmanov.laovay.mvp.model.core.vocabulary.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Example")
class ExampleWord(
    @PrimaryKey(autoGenerate = true)
    val id:Long?,
    val word:String?,
    val translate:String?
)