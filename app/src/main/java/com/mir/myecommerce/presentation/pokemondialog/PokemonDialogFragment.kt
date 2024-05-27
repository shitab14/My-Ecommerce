package com.mir.myecommerce.presentation.pokemondialog

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.mir.myecommerce.R
import com.mir.myecommerce.databinding.FragmentPokemonDialogBinding
import com.mir.myecommerce.presentation.listpage.PokemonItem


/**
Created by Shitab Mir on 26/5/24.
shitabmir@gmail.com
 **/
class PokemonDialogFragment(private val item: PokemonItem) : DialogFragment() {

    private lateinit var binding: FragmentPokemonDialogBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setCancelable(false) // Prevent dismissing by touching outside
        dialog.setCanceledOnTouchOutside(false) // Prevent dismissing by touching outside
        return dialog
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment using data binding
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_pokemon_dialog, container, false)
        binding.dialogFragment = this

        binding.tvDialogTitle.text = item.name
        binding.tvDialogMsg.text = item.url

        return binding.root
    }

    fun openLinkOnBottomSheet(view: View) {
        val dialogFragment = PokemonBottomSheetDialogFragment(item)
        dialogFragment.show(childFragmentManager, "pokemonBottomSheetDialogFragment")
    }

    fun onCloseClicked(view: View) {
        dismiss()
    }
}