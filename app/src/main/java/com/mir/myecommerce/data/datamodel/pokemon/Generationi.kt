package com.mir.myecommerce.data.datamodel.pokemon

import com.google.gson.annotations.SerializedName


data class Generationi (

  @SerializedName("red-blue" ) var redblue : Redblue? = Redblue(),
  @SerializedName("yellow"   ) var yellow   : Yellow?   = Yellow()

)