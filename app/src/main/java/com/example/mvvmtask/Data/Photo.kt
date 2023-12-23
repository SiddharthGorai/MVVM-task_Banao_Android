package com.example.mvvmtask.Data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Photo(
    val farm: Int,
    val height_s: Int,
    val id: String,
    val isfamily: Int,
    val isfriend: Int,
    val ispublic: Int,
    val owner: String,
    val secret: String,
    val server: String,
    val title: String,
    val url_s: String,
    val width_s: Int
) : Parcelable