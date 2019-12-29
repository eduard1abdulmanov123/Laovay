package com.abdulmanov.laovay.dagger.component

import com.abdulmanov.laovay.dagger.module.FragmentModule
import com.abdulmanov.laovay.dagger.scope.FragmentScope
import com.abdulmanov.laovay.ui.fragments.LibraryFragment
import com.abdulmanov.laovay.ui.fragments.SearchFragment
import dagger.Subcomponent

@FragmentScope
@Subcomponent(modules = [FragmentModule::class])
interface FragmentComponent {

    fun inject(libraryFragment: LibraryFragment)

    fun inject(searchFragment: SearchFragment)
}