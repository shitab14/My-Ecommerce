package com.mir.myecommerce.presentation.listpage

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mir.myecommerce.R

/**
Created by Shitab Mir on 24/5/24.
shitabmir@gmail.com
 **/
class ItemAdapter : ListAdapter<PokemonItem, ItemAdapter.ItemViewHolder>(DiffCallback()) {

 override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
  val view = LayoutInflater.from(parent.context).inflate(R.layout.item_pokemon_list, parent, false)
  return ItemViewHolder(view)
 }

 override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
  holder.bind(getItem(position))
 }

 class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
  private val tvName: TextView = itemView.findViewById(R.id.tvName)
  private val tvLink: TextView = itemView.findViewById(R.id.tvLink)

  fun bind(item: PokemonItem) {
   tvName.text = item.name
   tvLink.text = item.url

   tvLink.setOnClickListener {
    // SHITAB TODO open Dialog fragment
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