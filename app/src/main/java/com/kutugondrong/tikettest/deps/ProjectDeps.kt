package com.kutugondrong.tikettest.deps

import android.app.Application

import com.kutugondrong.tikettest.BaseAplication

import javax.inject.Singleton

import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dagger.android.support.AndroidSupportInjectionModule

@Singleton
@Component(modules = [AppModule::class, ActivityBindingModule::class, AndroidSupportInjectionModule::class, NetworkModule::class])
interface ProjectDeps : AndroidInjector<DaggerApplication> {

    fun inject(application: BaseAplication)

    override fun inject(instance: DaggerApplication)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): ProjectDeps
    }

}
