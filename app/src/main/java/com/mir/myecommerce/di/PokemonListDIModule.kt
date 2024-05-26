package com.mir.myecommerce.di

import android.content.Context
import com.mir.myecommerce.network.PokemonApiService
import com.mir.myecommerce.data.repository.pokemonlistdatarepository.PokemonListDataRepository
import com.mir.myecommerce.data.repository.pokemonlistdatarepository.PokemonListDataRepositoryImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

/**
Created by Shitab Mir on 25/5/24.
shitabmir@gmail.com
 **/

@Module
@InstallIn(SingletonComponent::class)
class PokemonListDIModule {

 @Provides
 @Singleton
 fun providePokemonListDataRepository(
  @ApplicationContext context: Context,
  @Named("pokemon_api_service") apiService: PokemonApiService
 ): PokemonListDataRepository {
  return PokemonListDataRepositoryImp(apiService)
 }

}