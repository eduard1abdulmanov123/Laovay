package com.abdulmanov.laovay.mvp.model.core.favorites

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.abdulmanov.laovay.mvp.model.core.common.Constants.UserDatabase.Companion.USER_DATABASE_NAME
import com.abdulmanov.laovay.mvp.model.core.common.Constants.UserDatabase.Companion.USER_DATABASE_VERSION
import com.abdulmanov.laovay.mvp.model.core.favorites.dao.UserDao
import com.abdulmanov.laovay.mvp.model.core.favorites.entities.FavoriteWord
import com.abdulmanov.laovay.mvp.model.core.favorites.entities.ViewedWord

@Database(
    entities = [FavoriteWord::class,ViewedWord::class],
    version = USER_DATABASE_VERSION,
    exportSchema = false
)
abstract class UserDatabase:RoomDatabase() {

    abstract fun getUserDao(): UserDao

    companion object {
        fun getDatabase(context: Context): UserDatabase {
            return Room.databaseBuilder(context, UserDatabase::class.java, USER_DATABASE_NAME)
                .build()
        }
    }
}