package com.mir.myecommerce.data.repository.fetchpokemonrepository

import com.mir.myecommerce.data.datamodel.pokemon.PokemonResponseDTO
import com.mir.myecommerce.network.PokemonApiService
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Named

/**
Created by Shitab Mir on 26/5/24.
shitabmir@gmail.com
 **/
class FetchPokemonRepositoryImp
@Inject constructor(
 @Named("pokemon_api_service")
 private val apiClient: PokemonApiService
): FetchPokemonRepository {
 override suspend fun fetchPokemonByAPIUrl(apiUrl: String): Response<PokemonResponseDTO> {
//  val apiUrlWithoutScheme = apiUrl.split(":")[1]
  return apiClient.getDirectPokemonByURL(apiUrlWithoutScheme = apiUrl)
 }
}