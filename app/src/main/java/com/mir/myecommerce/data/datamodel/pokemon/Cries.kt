package com.mir.myecommerce.data.datamodel.pokemon

import com.google.gson.annotations.SerializedName


data class Cries (

  @SerializedName("latest" ) var latest : String? = null,
  @SerializedName("legacy" ) var legacy : String? = null

)