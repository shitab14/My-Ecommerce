package com.mir.myecommerce.data.datamodel.pokemon

import com.google.gson.annotations.SerializedName


data class Generationv (

  @SerializedName("black-white" ) var blackwhite : Blackwhite? = Blackwhite()

)