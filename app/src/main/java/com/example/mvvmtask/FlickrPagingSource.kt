package com.example.mvvmtask

import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.mvvmtask.Data.Photo
import com.example.mvvmtask.Retrofit.apiInterface
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

private const val STARTING_PAGE = 1
class FlickrPagingSource(
    private val apiInterface: apiInterface, private val tags: String
): PagingSource<Int, Photo>() {
    private val apiKey = "6f102c62f41998d151e5a1b48713cf13"
    private val method = "flickr.photos.search"
    private val format = "json"
    private val noJsonCallBack = 1
//    private val tags = "dogs"
    private val extras = "url_s"
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Photo> {
        val position = params.key ?: STARTING_PAGE

        return  try{
            val response = apiInterface.getPhotos(method, apiKey, format, tags, extras, noJsonCallBack)
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