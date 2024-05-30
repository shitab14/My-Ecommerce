package com.mir.testermodule.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.mir.testermodule.R
import com.mir.testermodule.databinding.FragmentHomeBinding
import com.mir.testermodule.presentation.MultiTypeAdapter
import dagger.hilt.android.AndroidEntryPoint

/**
Created by Shitab Mir on 28/5/24.
shitabmir@gmail.com
 **/
@AndroidEntryPoint
class HomeFragment :Fragment() {

    private val viewModel by viewModels<HomeViewModel>()
    private lateinit var binding: FragmentHomeBinding

    private lateinit var adapter: MultiTypeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)

        binding.homeFragment = this

        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.response.observe(viewLifecycleOwner) { response ->
            adapter = MultiTypeAdapter(response.dynamicViews)
            binding.recyclerView.adapter = adapter
        }
// SHITAB TODO Optimize API Call
        viewModel.fetchData()
    }
}