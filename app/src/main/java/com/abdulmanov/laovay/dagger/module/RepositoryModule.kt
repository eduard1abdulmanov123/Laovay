package com.abdulmanov.laovay.dagger.module

import com.abdulmanov.laovay.mvp.model.core.favorites.dao.UserDao
import com.abdulmanov.laovay.mvp.model.core.vocabulary.dao.VocabularyDao
import com.abdulmanov.laovay.mvp.model.domain.helpers.LanguageCodeDetection
import com.abdulmanov.laovay.mvp.model.domain.converters.Converter
import com.abdulmanov.laovay.mvp.model.domain.converters.ConverterImpl
import com.abdulmanov.laovay.mvp.model.domain.helpers.LanguageCodeDetectionImpl
import com.abdulmanov.laovay.mvp.model.domain.repository.Repository
import com.abdulmanov.laovay.mvp.model.domain.repository.RepositoryImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Singleton
    @Provides
    fun provideConverter():Converter{
        return ConverterImpl()
    }

    @Singleton
    @Provides
    fun provideLanguageCodeDetection(): LanguageCodeDetection{
        return LanguageCodeDetectionImpl()
    }

    @Singleton
    @Provides
    fun provideRepository(
        vocabularyDao: VocabularyDao,
        userDao: UserDao,
        languageCodeDetection: LanguageCodeDetection,
        converter: Converter
    ):Repository{
        return RepositoryImpl(vocabularyDao,userDao,languageCodeDetection,converter)
    }
}