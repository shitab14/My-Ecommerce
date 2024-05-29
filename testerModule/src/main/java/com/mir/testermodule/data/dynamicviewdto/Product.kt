package com.mir.testermodule.data.dynamicviewdto

import com.google.gson.annotations.SerializedName

/**
Created by Shitab Mir on 29/5/24.
shitabmir@gmail.com
 **/
data class Product (
 @SerializedName("name") var name: String? = null,
 @SerializedName("image") var image: String? = null,
)