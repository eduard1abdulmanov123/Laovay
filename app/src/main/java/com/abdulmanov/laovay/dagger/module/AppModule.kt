package com.abdulmanov.laovay.dagger.module

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val context:Context) {

    @Singleton
    @Provides
    fun provideContext():Context{
        return context
    }
}