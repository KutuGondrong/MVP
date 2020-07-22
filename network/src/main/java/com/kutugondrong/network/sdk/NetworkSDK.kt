package com.kutugondrong.network.sdk


import com.kutugondrong.network.networks.NetworkService
import com.kutugondrong.network.model.ResponseItem
import com.kutugondrong.network.response.ResponseCallback
import io.reactivex.disposables.CompositeDisposable

class NetworkSDK(private val service: NetworkService) :
    Network {

    override fun search(
        searchText: String,
        compositeDisposable: CompositeDisposable,
        callback: ResponseCallback<ResponseItem>
    ) {
        val disposable = service.search(searchText, callback)
        compositeDisposable.add(disposable)
    }

}