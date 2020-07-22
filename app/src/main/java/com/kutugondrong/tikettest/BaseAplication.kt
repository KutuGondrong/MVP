package com.kutugondrong.tikettest

import android.annotation.SuppressLint
import android.content.Context
import androidx.multidex.MultiDex
import com.kutugondrong.tikettest.deps.DaggerProjectDeps

import com.kutugondrong.tikettest.deps.ProjectDeps

import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class BaseAplication : DaggerApplication() {

    private var deps: ProjectDeps? = null

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    @SuppressLint("CheckResult")
    override fun onCreate() {
        super.onCreate()
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        deps = DaggerProjectDeps.builder().application(this).build()
        deps!!.inject(this)
        return deps as ProjectDeps
    }
}