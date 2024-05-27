package com.mir.myecommerce.data.datamodel.pokemon

import com.google.gson.annotations.SerializedName


data class Move (

  @SerializedName("name" ) var name : String? = null,
  @SerializedName("url"  ) var url  : String? = null

)