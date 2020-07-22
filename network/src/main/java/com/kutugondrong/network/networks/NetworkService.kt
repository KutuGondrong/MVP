package com.kutugondrong.network.networks


import com.kutugondrong.network.model.ResponseItem
import com.kutugondrong.network.rx.BaseSchedulerProvider
import com.kutugondrong.network.response.ResponseCallback
import io.reactivex.disposables.Disposable

class NetworkService(private val service: Service,
                     private val schedulers: BaseSchedulerProvider) {

    fun search(searchText: String, callback: ResponseCallback<ResponseItem>): Disposable {
        return service.search(searchText)
                .observeOn(schedulers.ui())
                .subscribeOn(schedulers.io())
                .subscribe({ response -> callback.onSuccess(response) }, { throwable -> callback.onError(throwable) })
    }


}
