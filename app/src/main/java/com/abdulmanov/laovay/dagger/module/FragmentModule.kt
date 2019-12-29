package com.abdulmanov.laovay.dagger.module

import com.abdulmanov.laovay.dagger.scope.FragmentScope
import com.abdulmanov.laovay.mvp.model.domain.repository.Repository
import com.abdulmanov.laovay.mvp.presenters.list.LibraryPresenter
import com.abdulmanov.laovay.mvp.presenters.list.SearchPresenter
import dagger.Module
import dagger.Provides

@Module
class FragmentModule {

    @FragmentScope
    @Provides
    fun provideLibraryPresenter(repository:Repository): LibraryPresenter {
        return LibraryPresenter(repository)
    }

    @FragmentScope
    @Provides
    fun provideSearchPresenter(repository:Repository): SearchPresenter {
        return SearchPresenter(repository)
    }
}