package com.mir.testermodule.data.dynamicviewdto

import com.google.gson.annotations.SerializedName


data class Content (

    @SerializedName("products") var products : List<Product>? = null,
    @SerializedName("image") var image : String? = null,
    @SerializedName("imageRatioWidth") var imageRatioWidth : Int?    = null,
    @SerializedName("imageRatioHeight") var imageRatioHeight : Int?    = null

)