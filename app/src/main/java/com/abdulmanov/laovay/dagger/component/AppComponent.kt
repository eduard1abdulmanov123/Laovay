package com.abdulmanov.laovay.dagger.component

import com.abdulmanov.laovay.dagger.module.*
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class,DatabaseModule::class,RepositoryModule::class])
interface AppComponent {

    fun fragmentComponent(fragmentModule: FragmentModule):FragmentComponent

    fun activityComponent(activityModule: ActivityModule):ActivityComponent

}