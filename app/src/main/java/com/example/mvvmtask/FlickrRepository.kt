package com.example.mvvmtask

import javax.inject.Inject
import javax.inject.Singleton
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData

@Singleton
class FlickrRepository @Inject constructor(
    private val apiInterface: apiInterface) {

    fun getResults() = Pager(
        config = PagingConfig(
                pageSize = 20,
                maxSize = 100,
                enablePlaceholders = false
                ),
        pagingSourceFactory =  { FlickrPagingSource(apiInterface)}
    ).liveData
}