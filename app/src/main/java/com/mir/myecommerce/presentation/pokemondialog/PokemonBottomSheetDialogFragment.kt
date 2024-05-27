package com.mir.myecommerce.presentation.pokemondialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mir.myecommerce.R
import com.mir.myecommerce.common.ImageUtil
import com.mir.myecommerce.databinding.FragmentPokemonBottomSheetDialogBinding
import com.mir.myecommerce.presentation.listpage.PokemonItem
import dagger.hilt.android.AndroidEntryPoint

/**
Created by Shitab Mir on 26/5/24.
shitabmir@gmail.com
 **/
@AndroidEntryPoint
class PokemonBottomSheetDialogFragment(
 private val item: PokemonItem
): BottomSheetDialogFragment() {
 private lateinit var binding: FragmentPokemonBottomSheetDialogBinding

 private val viewModel by viewModels<PokemonBottomSheetDialogViewModel>()
 override fun onCreateView(
  inflater: LayoutInflater, container: ViewGroup?,
  savedInstanceState: Bundle?
 ): View {
//  val view = inflater.inflate(R.layout.fragment_pokemon_bottom_sheet_dialog, container, false)
//  val textView: TextView = view.findViewById(R.id.textView)
  binding = DataBindingUtil
   .inflate(inflater, R.layout.fragment_pokemon_bottom_sheet_dialog, container, false)
  binding.bottomSheetDialogFragment = this

  setObserver()
  viewModel.fetchThisPokemon(item.url)

  binding.tvName.text = item.name

//  return view
  return binding.root
 }

 private fun setObserver() {
  viewModel.pokemonData.observe(this) { it ->
   binding.tvName.text = it.name


   ImageUtil.loadImageByUrl(
    fragment = this@PokemonBottomSheetDialogFragment,
    supportsCache = true,
    imageUrl = it.image,
    imageView = binding.ivPokemon
   )

  }
 }

 fun onCloseClicked(view: View) {
  dismiss()
 }

}