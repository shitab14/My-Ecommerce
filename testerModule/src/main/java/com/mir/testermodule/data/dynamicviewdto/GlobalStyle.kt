package com.mir.testermodule.data.dynamicviewdto

import com.google.gson.annotations.SerializedName


data class GlobalStyle (

  @SerializedName("backgroundColor"         ) var backgroundColor         : String? = null,
  @SerializedName("forgroundColor"          ) var forgroundColor          : String? = null,
  @SerializedName("paddingTop"              ) var paddingTop              : Int?    = null,
  @SerializedName("paddingBottom"           ) var paddingBottom           : Int?    = null,
  @SerializedName("paddingLeft"             ) var paddingLeft             : Int?    = null,
  @SerializedName("paddingRight"            ) var paddingRight            : Int?    = null,
  @SerializedName("cornerRadiusTopLeft"     ) var cornerRadiusTopLeft     : Int?    = null,
  @SerializedName("cornerRadiusTopRight"    ) var cornerRadiusTopRight    : Int?    = null,
  @SerializedName("cornerRadiusBottomLeft"  ) var cornerRadiusBottomLeft  : Int?    = null,
  @SerializedName("cornerRadiusBottomRight" ) var cornerRadiusBottomRight : Int?    = null,
  @SerializedName("backgroundImage"         ) var backgroundImage         : String? = null

)