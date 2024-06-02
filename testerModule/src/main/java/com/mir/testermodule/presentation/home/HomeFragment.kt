package com.mir.testermodule.presentation.home

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.mir.commonlibrary.imagelib.ImageUtil
import com.mir.testermodule.R
import com.mir.testermodule.databinding.FragmentHomeBinding
import com.mir.testermodule.presentation.DynamicViewActivityViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
Created by Shitab Mir on 28/5/24.
shitabmir@gmail.com
 **/
@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel by viewModels<HomeViewModel>()
    private val activityViewModel: DynamicViewActivityViewModel by viewModels()

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

            binding.clHomeParentView.setPadding(
                response.globalStyle?.paddingLeft ?: 0,
                response.globalStyle?.paddingTop ?: 0,
                response.globalStyle?.paddingRight ?: 0,
                response.globalStyle?.paddingBottom ?: 0
            )
            if(!response.globalStyle?.backgroundColor.isNullOrBlank()) {
                binding.clHomeParentView.setBackgroundColor(
                    Color.parseColor(response.globalStyle?.backgroundColor ?: "#FFFFFF")
                )
            }
            if(!response.globalStyle?.backgroundImage.isNullOrBlank()) {
                binding.clHomeParentView.setBackgroundColor(Color.TRANSPARENT)
                response.globalStyle?.backgroundImage?.let {
                    ImageUtil.loadImageByUrl(
                        context = view.context,
                        supportsCache = true,
                        imageUrl = it,
                        imageView = binding.ivParentBackgroundImage
                    )
                }
            }

            val cornerRadiusTopLeft = response.globalStyle?.cornerRadiusTopLeft?.toFloat() ?: 0F
            /*val cornerRadiusTopRight = response.globalStyle?.cornerRadiusTopRight?.toFloat() ?: 0F
            val cornerRadiusBottomLeft = response.globalStyle?.cornerRadiusBottomLeft?.toFloat() ?: 0F
            val cornerRadiusBottomRight = response.globalStyle?.cornerRadiusBottomRight?.toFloat() ?: 0F

            // Define custom ShapeAppearanceModel
            val shapeAppearanceModel = ShapeAppearanceModel.builder()
                .setTopLeftCorner(CornerFamily.ROUNDED, cornerRadiusTopLeft) // Top-left corner radius in pixels
                .setTopRightCorner(CornerFamily.ROUNDED, cornerRadiusTopRight) // Top-right corner radius in pixels
                .setBottomLeftCorner(CornerFamily.ROUNDED, cornerRadiusBottomLeft) // Bottom-left corner radius in pixels
                .setBottomRightCorner(CornerFamily.ROUNDED, cornerRadiusBottomRight) // Bottom-right corner radius in pixels
                .build()

            // Apply the shape appearance model to the card
            binding.cvParentView.shapeAppearanceModel = shapeAppearanceModel*/

            binding.cvParentView.radius = cornerRadiusTopLeft

            binding.clInsideParent.setBackgroundColor(
                Color.parseColor(response.globalStyle?.forgroundColor ?: "#FFFFFF")
            )
        }

        fetchAPIData()
    }

    fun fetchAPIData() {
        viewModel.fetchData()
    }

}