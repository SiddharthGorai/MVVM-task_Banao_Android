package com.example.mvvmtask

import javax.inject.Inject
import javax.inject.Singleton
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.example.mvvmtask.Retrofit.RetrofitInstance
import com.example.mvvmtask.Retrofit.apiInterface

@Singleton
class FlickrRepository @Inject constructor(
    private val apiInterface: apiInterface
) {

        fun getResults(query: String) = Pager(
        config = PagingConfig(
                pageSize = 20,
                maxSize = 100,
                enablePlaceholders = false
                ),
        pagingSourceFactory =  { FlickrPagingSource(apiInterface,query)}
    ).liveData


}