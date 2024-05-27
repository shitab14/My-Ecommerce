package com.mir.myecommerce.data.repository.fetchpokemonrepository

import com.mir.myecommerce.data.datamodel.pokemon.PokemonResponseDTO
import retrofit2.Response

/**
Created by Shitab Mir on 26/5/24.
shitabmir@gmail.com
 **/
interface FetchPokemonRepository {
    suspend fun fetchPokemonByAPIUrl(apiUrl: String): Response<PokemonResponseDTO>

}