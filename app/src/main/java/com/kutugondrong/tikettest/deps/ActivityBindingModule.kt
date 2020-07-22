package com.kutugondrong.tikettest.deps

import com.kutugondrong.tikettest.main.MainActivity
import com.kutugondrong.tikettest.main.MainModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {

    @ActivityScoped
    @ContributesAndroidInjector(modules = [MainModule::class])
    internal abstract fun mainActivity(): MainActivity

}
