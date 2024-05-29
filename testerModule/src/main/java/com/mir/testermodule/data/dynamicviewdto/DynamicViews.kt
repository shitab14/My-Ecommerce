package com.mir.testermodule.data.dynamicviewdto

import com.google.gson.annotations.SerializedName


data class DynamicViews (

    @SerializedName("viewType" ) var viewType : String?  = null,
    @SerializedName("style"    ) var style    : Style?   = Style(),
    @SerializedName("content"  ) var content  : Content? = Content()

)