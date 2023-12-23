package com.example.mvvmtask

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.mvvmtask.Data.Photo
import com.example.mvvmtask.Retrofit.apiInterface
import retrofit2.HttpException
import java.io.IOException

private const val STARTING_PAGE = 1
class FlickrPagingSource(
    private val apiInterface: apiInterface
): PagingSource<Int, Photo>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Photo> {
        val position = params.key ?: STARTING_PAGE

        return  try{
            val response = apiInterface.getPhotos()
            val responsePhotos = response.photos.photo

            LoadResult.Page(
                data = responsePhotos,
                prevKey = if (position == STARTING_PAGE) null else position -1,
                nextKey = if(responsePhotos.isEmpty()) null else position + 1
            )

        } catch (exception: IOException){
            LoadResult.Error(exception)
        } catch (exception: HttpException){
            LoadResult.Error(exception)
        }

    }

    override fun getRefreshKey(state: PagingState<Int, Photo>): Int? {
        TODO("Not yet implemented")
    }
}