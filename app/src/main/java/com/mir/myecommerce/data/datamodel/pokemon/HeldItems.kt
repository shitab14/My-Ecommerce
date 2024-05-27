package com.mir.myecommerce.data.datamodel.pokemon

import com.google.gson.annotations.SerializedName


data class HeldItems (

  @SerializedName("item"            ) var item           : Item?                     = Item(),
  @SerializedName("version_details" ) var versionDetails : ArrayList<VersionDetails> = arrayListOf()

)