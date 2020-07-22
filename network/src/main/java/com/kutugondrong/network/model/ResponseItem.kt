package com.kutugondrong.network.model

import com.bluelinelabs.logansquare.annotation.JsonField
import com.bluelinelabs.logansquare.annotation.JsonObject

@JsonObject
open class ResponseItem {

    @JsonField(name = ["total_count"])
    var total_count = 0
    @JsonField(name = ["items"])
    var items = ArrayList<Item>()
    constructor()
}

@JsonObject
class Item {
    @JsonField(name = ["full_name"])
    var full_name = ""
}