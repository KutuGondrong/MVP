package com.kutugondrong.network.sdk

import com.kutugondrong.network.model.ResponseItem
import com.kutugondrong.network.response.ResponseCallback

import io.reactivex.disposables.CompositeDisposable

interface Network {
    fun search(searchText: String, compositeDisposable: CompositeDisposable, callback: ResponseCallback<ResponseItem>)
}
