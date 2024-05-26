package com.mir.myecommerce.presentation.listpage

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mir.myecommerce.R
import com.mir.myecommerce.base.BaseActivity
import com.mir.myecommerce.databinding.ActivityListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListActivity : BaseActivity<ActivityListBinding>() {

//    private lateinit var viewModel: ListActivityViewModel
    val viewModel by viewModels<ListActivityViewModel>()

    private lateinit var recyclerView: RecyclerView
    private lateinit var itemAdapter: ItemAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_list)
//        viewModel = ViewModelProvider(this)[ListActivityViewModel::class.java]

        getDataFromPreviousActivity()

        setupRecyclerViewWithInfiniteScrolling()
        setupObservers()
        setupClickListener()
    }

    private fun setupObservers() {
        viewModel.isLoading().observe(this) { loading ->
            if (loading) {
                binding.progressBar.show()
            } else {
                binding.progressBar.hide()
            }
        }
    }

    private fun setupClickListener() {
        binding.btnFetchData.setOnClickListener {
            // Load initial data
            viewModel.loadMoreItems()
        }
    }

    private fun setupRecyclerViewWithInfiniteScrolling() {
        recyclerView = findViewById(R.id.recyclerView)
        itemAdapter = ItemAdapter()

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = itemAdapter

        // Observe the items LiveData from the ViewModel
        viewModel.items.observe(this) { items ->
            if (!binding.recyclerView.isVisible) binding.recyclerView.visibility = View.VISIBLE
            itemAdapter.submitList(items)
        }

        // infinite scroll
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerView.canScrollVertically(1)) {
                    viewModel.loadMoreItems()
                }
            }
        })
    }

    private fun getDataFromPreviousActivity() {
//        val stringExtraData = intent.getStringExtra("EXTRA_STRING_DATA")
        val bundle = intent.extras
        val bundleStringData = bundle?.getString("EXTRA_BUNDLE_STRING_DATA").toString()
        val bundleIntData = bundle?.getInt("EXTRA_BUNDLE_INT_DATA").toString()

        @SuppressLint("SetTextI18n")
        binding.tvDataFromPrevious.text =
//            "String Extra Data: $stringExtraData\n" +
                "String Bundle Data: $bundleStringData\n" +
                "Int Bundle Data: $bundleIntData\n"
    }
}