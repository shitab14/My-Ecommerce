package com.mir.testermodule.presentation.uitesting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.mir.commonlibrary.ui.MyCustomBottomSheetFragment
import com.mir.commonlibrary.ui.MyCustomDialogFragment
import com.mir.commonlibrary.ui.UIUtil
import com.mir.testermodule.R
import com.mir.testermodule.databinding.FragmentServicesBinding
import dagger.hilt.android.AndroidEntryPoint

/**
Created by Shitab Mir on 28/5/24.
shitabmir@gmail.com
 **/
@AndroidEntryPoint
class UITestingFragment : Fragment() {
    private lateinit var binding: FragmentServicesBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_services, container, false)

        binding.tvOpenDialog.setOnClickListener {
            UIUtil.showCustomDialog(
                context = requireContext(),
                title = "This is Title",
                message = "This is Message",
                positiveButtonText = "Yeah!",
                negativeButtonText = "Nuh Uh!",
                cancelable = true,
                dismissibleOnTouchOutside = true,
                onPositiveButtonClick = {
                    UIUtil.showToast(requireContext(), "Yeah!", Toast.LENGTH_LONG)
                },
                onNegativeButtonClick = {
                    UIUtil.showToast(requireContext(), "Nuh Uh!", Toast.LENGTH_LONG)
                }
            )
        }

        binding.tvOpenDialogFragment.setOnClickListener {
            UIUtil.showCustomDialogFragment(
                fragmentManager = childFragmentManager,
                dialogFragment = MyCustomDialogFragment(),
                tag = "myCustomDialog"
            )
        }

        binding.tvOpenBottomSheet.setOnClickListener {
            UIUtil.showCustomBottomSheet(requireContext(), R.layout.bottom_sheet_common)
        }

        binding.tvOpenBottomSheetFragment.setOnClickListener {
            UIUtil.showCustomBottomSheetFragment(
                fragmentManager = childFragmentManager,
                bottomSheetFragment = MyCustomBottomSheetFragment(),
                tag = "myCustomBottomSheet"
            )
        }
        return binding.root
//        return super.onCreateView(inflater, container, savedInstanceState)
    }

}