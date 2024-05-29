package com.mir.testermodule.data.dynamicviewdto

import com.google.gson.annotations.SerializedName


data class DynamicViewPageResponse (
    @SerializedName("code") var code : Int?  = null,
    @SerializedName("message") var message: String? = null,
    @SerializedName("globalStyle") var globalStyle : GlobalStyle? = GlobalStyle(),
    @SerializedName("dynamicViews") var dynamicViews : ArrayList<DynamicViews> = arrayListOf()

)