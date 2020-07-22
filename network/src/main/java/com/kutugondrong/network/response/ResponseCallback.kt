package com.kutugondrong.network.response

interface ResponseCallback<T> {
    fun onError(throwable: Throwable)
    fun onSuccess(response: T)
}
