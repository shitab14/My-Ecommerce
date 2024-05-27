package com.mir.myecommerce.data.datamodel.pokemon

import com.google.gson.annotations.SerializedName


data class Generationiii (

  @SerializedName("emerald"           ) var emerald           : Emerald?           = Emerald(),
  @SerializedName("firered-leafgreen" ) var fireredleafgreen : Fireredleafgreen? = Fireredleafgreen(),
  @SerializedName("ruby-sapphire"     ) var rubysapphire     : Rubysapphire?     = Rubysapphire()

)