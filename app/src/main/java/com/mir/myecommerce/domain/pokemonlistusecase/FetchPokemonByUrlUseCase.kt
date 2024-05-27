package com.mir.myecommerce.domain.pokemonlistusecase

import android.util.Log
import com.google.gson.Gson
import com.mir.myecommerce.data.datamodel.pokemon.PokemonResponseDTO
import com.mir.myecommerce.data.repository.fetchpokemonrepository.FetchPokemonRepository
import com.mir.myecommerce.network.ErrorMessage
import com.mir.myecommerce.network.State
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

/**
Created by Shitab Mir on 26/5/24.
shitabmir@gmail.com
 **/
class FetchPokemonByUrlUseCase
@Inject constructor(private val repository: FetchPokemonRepository) {
 operator fun invoke(url: String): Flow<State<PokemonResponseDTO>> = flow {

  try {
   emit(State.Loading)
   repository.fetchPokemonByAPIUrl(url).let { response ->
    if (response.isSuccessful) {
     response.body()?.let {
      Log.d("FetchPokemonByUrlUseCase", "Passed ${Gson().toJson(it)}")
      emit(State.Success(it))
     } ?: kotlin.run {
      emit(State.Failed(ErrorMessage.SOMETHING_WENT_WRONG))
     }

    } else {
     Log.d("FetchPokemonByUrlUseCase", "failed")
     if (response.code() == 401) {
      emit(State.Failed(ErrorMessage.UNAUTHORIZED_USER, response.code()))
     } else {
      emit(State.Failed(ErrorMessage.SOMETHING_WENT_WRONG))
     }
    }

   }

  } catch (e: Exception) {
   Log.d("AppUseCase", "exception")
   e.printStackTrace()
   emit(State.Failed(ErrorMessage.SOMETHING_WENT_WRONG))
  }


 }
}