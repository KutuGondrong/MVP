package com.kutugondrong.tikettest.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kutugondrong.network.model.ResponseItem
import com.kutugondrong.tikettest.R
import dagger.android.AndroidInjection
import javax.inject.Inject

class MainActivity : AppCompatActivity(), MainContract.View {

    @Inject
    lateinit var presenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter.search("ojol")
    }

    override fun loading() {

    }

    override fun dismissLoading() {

    }

    override fun failedNetwork() {

    }

    override fun success(response: ResponseItem) {

    }

    override fun empty() {

    }
}
