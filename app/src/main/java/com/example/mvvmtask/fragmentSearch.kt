package com.example.mvvmtask

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import android.widget.Toolbar
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvmtask.databinding.FragmentHomeBinding
import com.example.mvvmtask.databinding.FragmentSearchBinding
import com.example.mvvmtask.viewModel.myViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class fragmentSearch : Fragment(R.layout.fragment_search) {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val viewModel: myViewModel by viewModels()
    private lateinit var recyclerView: RecyclerView
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSearchBinding.bind(view)
        val adapter = pagingDataAdapter()
        recyclerView = binding.fragRecyclerView
        val layoutManager = GridLayoutManager(context, 2)
        recyclerView.setHasFixedSize(true)

        binding.btnRetry.setOnClickListener {
            adapter.retry()
        }

        val footerAdapter = loadStateAdapter { adapter.retry() }
        recyclerView.adapter = adapter.withLoadStateFooter(footer = footerAdapter)
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (position == adapter.itemCount && footerAdapter.itemCount > 0) {
                    2
                } else {
                    1
                }
            }
        }
        recyclerView.layoutManager = layoutManager


        val bundle = arguments
        val query = bundle?.getString("mText")

        if (query != null) {
            viewModel.searchPhotos(query)
        }



        viewModel.photos.observe(viewLifecycleOwner) {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }


        adapter.addLoadStateListener { loadState ->
            binding.apply {
                progBar.isVisible = loadState.source.refresh is LoadState.Loading
                recyclerView.isVisible = loadState.source.refresh is LoadState.NotLoading
                btnRetry.isVisible = loadState.source.refresh is LoadState.Error
                txtErr.isVisible = loadState.source.refresh is LoadState.Error

                if (loadState.source.refresh is LoadState.NotLoading &&
                    loadState.append.endOfPaginationReached && adapter.itemCount < 1
                ) {
                    recyclerView.isVisible = false
                    txtNores.isVisible = true

                }

            }
        }


    }
}