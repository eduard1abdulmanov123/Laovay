package com.abdulmanov.laovay.dagger.component

import com.abdulmanov.laovay.dagger.module.ActivityModule
import com.abdulmanov.laovay.dagger.scope.ActivityScope
import com.abdulmanov.laovay.ui.activities.DetailsWordActivity
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = [ActivityModule::class])
interface ActivityComponent {

    fun inject(detailsWordActivity: DetailsWordActivity)

}