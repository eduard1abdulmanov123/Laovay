package com.abdulmanov.laovay.app

import android.app.Application
import com.abdulmanov.laovay.dagger.component.ActivityComponent
import com.abdulmanov.laovay.dagger.component.AppComponent
import com.abdulmanov.laovay.dagger.component.DaggerAppComponent
import com.abdulmanov.laovay.dagger.component.FragmentComponent
import com.abdulmanov.laovay.dagger.module.ActivityModule
import com.abdulmanov.laovay.dagger.module.AppModule
import com.abdulmanov.laovay.dagger.module.FragmentModule
import io.reactivex.plugins.RxJavaPlugins

class BaseApp:Application() {

    companion object {
        lateinit var instance: BaseApp private set
    }

    lateinit var appComponent: AppComponent

    private var activityComponent: ActivityComponent? = null
    private var fragmentComponent: FragmentComponent? = null

    override fun onCreate() {
        super.onCreate()
        instance = this
        appComponent = DaggerAppComponent.builder().appModule(AppModule(this)).build()
        RxJavaPlugins.setErrorHandler { }
    }

    fun plusActivityComponent():ActivityComponent{
        if(activityComponent==null){
            activityComponent = appComponent.activityComponent(ActivityModule())
        }
        return activityComponent!!
    }

    fun clearActivityComponent(){
        activityComponent = null
    }

    fun plusFragmentComponent():FragmentComponent{
        if(fragmentComponent==null){
            fragmentComponent = appComponent.fragmentComponent(FragmentModule())
        }
        return fragmentComponent!!
    }

    fun clearFragmentComponent(){
        fragmentComponent = null
    }
}