package com.kutugondrong.tikettest.main

import com.kutugondrong.network.model.ResponseItem

class MainContract {

    interface View{
        fun loading()
        fun dismissLoading()
        fun failedNetwork()
        fun success(response: ResponseItem)
        fun empty()
    }

    interface Presenter{
        fun search(txt: String)
    }
}
