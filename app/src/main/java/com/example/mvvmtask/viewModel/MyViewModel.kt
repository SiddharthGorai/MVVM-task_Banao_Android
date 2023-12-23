package com.example.mvvmtask.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.mvvmtask.FlickrRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class myViewModel @Inject constructor(
    private val repository: FlickrRepository
) : ViewModel(){

        val photos = repository.getResults().cachedIn(viewModelScope)
}