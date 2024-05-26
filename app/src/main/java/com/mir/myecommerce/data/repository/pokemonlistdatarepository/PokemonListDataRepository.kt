package com.mir.myecommerce.data.repository.pokemonlistdatarepository

import com.mir.myecommerce.data.datamodel.PokemonResponseDTO
import retrofit2.Response

/**
Created by Shitab Mir on 25/5/24.
shitabmir@gmail.com
 **/
interface PokemonListDataRepository {
 suspend fun getPokemonListResponseData(offset: Int, limit: Int): Response<PokemonResponseDTO>

}