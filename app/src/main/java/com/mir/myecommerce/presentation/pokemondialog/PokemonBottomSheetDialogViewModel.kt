package com.mir.myecommerce.presentation.pokemondialog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mir.myecommerce.base.BaseViewModel
import com.mir.myecommerce.domain.pokemonlistusecase.FetchPokemonByUrlUseCase
import com.mir.testermodule.networkduplicate.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
Created by Shitab Mir on 26/5/24.
shitabmir@gmail.com
 **/
@HiltViewModel
class PokemonBottomSheetDialogViewModel
@Inject constructor(
 private val useCase: FetchPokemonByUrlUseCase
): BaseViewModel() {

 private val _message = MutableLiveData<String>()
 val message: LiveData<String> = _message

 private val _pokemonData = MutableLiveData<PokemonModel>()
 val pokemonData: LiveData<PokemonModel> = _pokemonData
 fun fetchThisPokemon(apiUrl: String) {
  viewModelScope.launch {

   useCase.invoke(apiUrl).collect { it ->

    when (it) {
     is State.Loading -> {
      _message.value = "LOADING!!!"
     }
     is State.Success -> {
      _message.value = "GOT IT:" + it.data.name.toString()
      _pokemonData.value = PokemonModel(
       name = it.data.name.toString(),
       image = it.data.sprites?.frontDefault.toString()
      )
     }
     is State.Failed -> {
      _message.value = "FAILED!!!"
     }

    }

   }
  }
 }

}