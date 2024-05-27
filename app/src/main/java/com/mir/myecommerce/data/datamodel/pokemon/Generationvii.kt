package com.mir.myecommerce.data.datamodel.pokemon

import com.google.gson.annotations.SerializedName


data class Generationvii (

  @SerializedName("icons"                ) var icons                : Icons?                = Icons(),
  @SerializedName("ultra-sun-ultra-moon" ) var ultrasunultramoon : Ultrasunultramoon? = Ultrasunultramoon()

)