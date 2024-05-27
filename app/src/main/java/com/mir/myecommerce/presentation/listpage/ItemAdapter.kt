package com.mir.myecommerce.presentation.listpage

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mir.myecommerce.R
import com.mir.myecommerce.presentation.pokemondialog.PokemonBottomSheetDialogFragment
import com.mir.myecommerce.presentation.pokemondialog.PokemonDialogFragment

/**
Created by Shitab Mir on 24/5/24.
shitabmir@gmail.com
 **/
class ItemAdapter(private val fragmentManager: FragmentManager) : ListAdapter<PokemonItem, ItemAdapter.ItemViewHolder>(DiffCallback()) {

 override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
  val view = LayoutInflater.from(parent.context).inflate(R.layout.item_pokemon_list, parent, false)
  return ItemViewHolder(view)
 }

 override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
  holder.bind(getItem(position), fragmentManager)
 }

 class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
  private val tvName: TextView = itemView.findViewById(R.id.tvName)
  private val tvLink: TextView = itemView.findViewById(R.id.tvLink)

  fun bind(item: PokemonItem, fragmentManager: FragmentManager) {
   tvName.text = item.name
   tvLink.text = item.url

   tvName.setOnClickListener {
    val dialogFragment = PokemonDialogFragment(item)
    dialogFragment.show(fragmentManager, "pokemonDialogDialog")
   }
   tvLink.setOnClickListener {
    val dialogFragment = PokemonBottomSheetDialogFragment(item)
    dialogFragment.show(fragmentManager, "pokemonBottomSheetDialogFragment")
   }

  }

 }

 class DiffCallback : DiffUtil.ItemCallback<PokemonItem>() {
  override fun areItemsTheSame(oldItem: PokemonItem, newItem: PokemonItem): Boolean {
   return oldItem.name == newItem.name
  }

  override fun areContentsTheSame(oldItem: PokemonItem, newItem: PokemonItem): Boolean {
   return oldItem == newItem
  }
 }
}