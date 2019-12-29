package com.abdulmanov.laovay.dagger.module

import com.abdulmanov.laovay.dagger.scope.ActivityScope
import com.abdulmanov.laovay.mvp.model.domain.repository.Repository
import com.abdulmanov.laovay.mvp.presenters.word.DetailsWordPresenter
import dagger.Module
import dagger.Provides

@Module
class ActivityModule {

    @ActivityScope
    @Provides
    fun provideDetailsWordPresenter(repository:Repository): DetailsWordPresenter {
        return DetailsWordPresenter(repository)
    }
}