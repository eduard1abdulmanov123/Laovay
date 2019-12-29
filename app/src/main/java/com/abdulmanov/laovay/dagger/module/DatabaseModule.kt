package com.abdulmanov.laovay.dagger.module

import android.content.Context
import android.util.Log
import com.abdulmanov.laovay.mvp.model.core.favorites.UserDatabase
import com.abdulmanov.laovay.mvp.model.core.favorites.dao.UserDao
import com.abdulmanov.laovay.mvp.model.core.vocabulary.VocabularyDatabase
import com.abdulmanov.laovay.mvp.model.core.vocabulary.dao.VocabularyDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideVocabularyDao(context: Context):VocabularyDao{
        return VocabularyDatabase.getDatabase(context).getVocabularyDao()
    }

    @Singleton
    @Provides
    fun provideUserDao(context: Context):UserDao{
        Log.d("DaggerCreate","userDao")
        return UserDatabase.getDatabase(context).getUserDao()
    }
}