package com.kutugondrong.network.networks

import com.kutugondrong.network.model.ResponseItem
import io.reactivex.Single
import retrofit2.http.*

interface Service {

    @GET("/search/repositories")
    fun search(@Query("q") request: String): Single<ResponseItem>

}
