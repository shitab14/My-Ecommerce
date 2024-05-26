package com.mir.myecommerce.data.datamodel

/**
Created by Shitab Mir on 25/5/24.
shitabmir@gmail.com
 **/
data class PokemonResponseDTO(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<PokemonItemDTO>
)
data class PokemonItemDTO(
    val url: String,
    val name: String
)