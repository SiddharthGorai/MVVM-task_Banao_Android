package com.example.mvvmtask.Retrofit

import com.example.mvvmtask.Data.photoData
import retrofit2.http.GET
import retrofit2.http.Query

interface apiInterface {

    @GET("rest")
    suspend fun getPhotos(
        @Query("method") method: String,
        @Query("api_key") api_key: String,
        @Query("format") format: String,
        @Query("tags") tags: String,
        @Query("extras") extras: String,
        @Query("nojsoncallback") nojsoncallback: Int
    ): photoData
}