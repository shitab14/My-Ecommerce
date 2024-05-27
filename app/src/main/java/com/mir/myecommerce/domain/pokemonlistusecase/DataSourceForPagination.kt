package com.mir.myecommerce.domain.pokemonlistusecase

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.mir.myecommerce.network.ErrorMessage
import com.mir.myecommerce.network.State
import com.mir.myecommerce.data.repository.pokemonlistdatarepository.PokemonListDataRepository
import com.mir.myecommerce.data.datamodel.PokemonListResponseDTO
import com.mir.myecommerce.presentation.listpage.PokemonItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

/**
Created by Shitab Mir on 24/5/24.
shitabmir@gmail.com
 **/
class DataSourceForPagination @Inject constructor(private val repository: PokemonListDataRepository) {
 // basically UseCase
 private var offset = 0
 private val limit = 50
 private val itemsLiveData = MutableLiveData<List<PokemonItem>>()

 private val tag = "DataSource:"
 fun getItems(): LiveData<List<PokemonItem>> {
  return itemsLiveData
 }

 operator fun invoke(): Flow<State<PokemonListResponseDTO>> = flow {
  try {
   emit(State.Loading)
   repository.getPokemonListResponseData(offset, limit).let { response ->
    if (response.isSuccessful && response.body()?.next != null) {
     response.body()?.let {
      Log.d(tag, "Passed ${Gson().toJson(it)}")
//       emit(State.Success(it))
      offset += limit
      val currentList = itemsLiveData.value.orEmpty().toMutableList()
      response.body()?.results?.forEach { itemDTO ->
       currentList.add(PokemonItem(url = itemDTO.url, name = itemDTO.name))
      }
//       currentList.addAll(response.body()?.results.orEmpty())
      itemsLiveData.value = currentList
     } ?: kotlin.run {
      emit(State.Failed(ErrorMessage.SOMETHING_WENT_WRONG))
     }

    } else {
     Log.d(tag, "failed")
     if (response.code() == 401) {
      emit(State.Failed(ErrorMessage.UNAUTHORIZED_USER, response.code()))
     } else {
      emit(State.Failed(ErrorMessage.SOMETHING_WENT_WRONG))
     }
    }

   }

  } catch (e: Exception) {
   Log.e(tag, "exception $e")
   e.printStackTrace()
   emit(State.Failed(ErrorMessage.SOMETHING_WENT_WRONG))
  }
 }

}