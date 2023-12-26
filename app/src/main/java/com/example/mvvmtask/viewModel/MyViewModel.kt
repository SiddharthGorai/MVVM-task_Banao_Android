package com.example.mvvmtask.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.mvvmtask.FlickrRepository
import dagger.Provides
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel

class myViewModel @Inject constructor(
    private val repository: FlickrRepository
) : ViewModel() {

    companion object {
        private const val DEFAULT_QUERY = "cats"
    }

    private val currentQuerry = MutableLiveData(DEFAULT_QUERY)

    val photos = currentQuerry.switchMap { query ->
        repository.getResults(query).cachedIn(viewModelScope)
    }

    fun searchPhotos(query:String){
        currentQuerry.value = query
    }
}