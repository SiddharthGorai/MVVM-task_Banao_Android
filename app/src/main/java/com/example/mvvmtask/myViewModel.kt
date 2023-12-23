package com.example.mvvmtask

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class myViewModel @Inject constructor(
    private val repository: FlickrRepository) : ViewModel(){

        val photos = repository.getResults().cachedIn(viewModelScope)
}