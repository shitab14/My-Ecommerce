package com.mir.myecommerce.data.repository.pokemonlistdatarepository

import com.mir.myecommerce.data.datamodel.PokemonListResponseDTO
import com.mir.myecommerce.network.PokemonApiService
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Named

/**
Created by Shitab Mir on 25/5/24.
shitabmir@gmail.com
 **/
class PokemonListDataRepositoryImp
@Inject constructor(@Named("pokemon_api_service") private val apiClient: PokemonApiService)
    : PokemonListDataRepository {
    override suspend fun getPokemonListResponseData(offset: Int, limit: Int): Response<PokemonListResponseDTO> {
        return apiClient.getPokemonListResponseData(offset = offset, limit = limit)
    }
}