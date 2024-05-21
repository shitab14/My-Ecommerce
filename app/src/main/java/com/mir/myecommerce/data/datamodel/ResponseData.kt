package com.mir.myecommerce.data.datamodel

import com.google.gson.annotations.SerializedName

/**
Created by Shitab Mir on 19/5/24.
shitabmir@gmail.com
 **/
data class ResponseData(
 @SerializedName("id")
 val id: Int,
 @SerializedName("data")
 val data: String
)