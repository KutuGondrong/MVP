package com.kutugondrong.tikettest.main

import com.kutugondrong.network.model.ResponseItem
import com.kutugondrong.network.response.ResponseCallback
import com.kutugondrong.network.sdk.NetworkSDK
import io.reactivex.disposables.CompositeDisposable

class MainPresenter(private val view: MainContract.View, private val networkSDK: NetworkSDK) : MainContract.Presenter {

    override fun search(txt: String) {
        view.loading()

        networkSDK.search(txt, compositeDisposable, object : ResponseCallback<ResponseItem> {
            override fun onError(throwable: Throwable) {
                view.dismissLoading()
                view.failedNetwork()
            }

            override fun onSuccess(response: ResponseItem) {
                view.dismissLoading()
                view.success(response)
            }

        })
    }

    private val compositeDisposable = CompositeDisposable()

}
