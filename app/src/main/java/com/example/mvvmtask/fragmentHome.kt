package com.example.mvvmtask
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvmtask.databinding.FragmentHomeBinding
import com.example.mvvmtask.viewModel.myViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class fragmentHome : Fragment(R.layout.fragment_home) {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: myViewModel by viewModels()
    private lateinit var recyclerView: RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentHomeBinding.bind(view)

        //Paging Adapter
        val adapter = pagingDataAdapter()
        recyclerView = binding.recyclerView
        val layoutManager = GridLayoutManager(context, 2)
        recyclerView.setHasFixedSize(true)


        val footerAdapter = loadStateAdapter {adapter.retry()}
        recyclerView.adapter = adapter.withLoadStateFooter(footer = footerAdapter)
        layoutManager.spanSizeLookup =  object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (position == adapter.itemCount  && footerAdapter.itemCount > 0) {
                    2
                } else {
                    1
                }
            }
        }
        recyclerView.layoutManager = layoutManager

        viewModel.photos.observe(viewLifecycleOwner) {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)

        }


    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



}