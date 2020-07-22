package com.kutugondrong.network.model

import com.bluelinelabs.logansquare.annotation.JsonField
import com.bluelinelabs.logansquare.annotation.JsonObject

@JsonObject
open class ResponseItem {

    @JsonField(name = ["total_count"])
    var total_count = 0

    constructor()
}