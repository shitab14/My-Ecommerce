package com.mir.myecommerce.network

import com.mir.myecommerce.data.datamodel.PokemonListResponseDTO
import com.mir.myecommerce.data.datamodel.pokemon.PokemonResponseDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

/**
Created by Shitab Mir on 24/5/24.
shitabmir@gmail.com
 **/
interface PokemonApiService {

 @GET("pokemon")
 // @GET("jsons/{path}")
 // https://pokeapi.co/api/v2/pokemon?limit=20&offset=0
 suspend fun getPokemonListResponseData(
  @Query("offset") offset: Int, // starts from 0
  @Query("limit") limit: Int, // set 20
 ): Response<PokemonListResponseDTO>

 @GET
 suspend fun getDirectPokemonByURL(
  @Url apiUrlWithoutScheme: String
//  @Path("apiUrl") apiUrlWithoutScheme: String
 ): Response<PokemonResponseDTO>

}