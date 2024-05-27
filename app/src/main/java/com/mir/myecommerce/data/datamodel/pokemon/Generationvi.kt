package com.mir.myecommerce.data.datamodel.pokemon

import com.google.gson.annotations.SerializedName


data class Generationvi (

  @SerializedName("omegaruby-alphasapphire" ) var omegarubyalphasapphire : Omegarubyalphasapphire? = Omegarubyalphasapphire(),
  @SerializedName("x-y"                     ) var xy                     : Xy?                     = Xy()

)