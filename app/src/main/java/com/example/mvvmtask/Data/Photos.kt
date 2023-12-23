package com.example.mvvmtask.Data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Photos(
    val page: Int,
    val pages: Int,
    val perpage: Int,
    val photo: List<Photo>,
    val total: Int
) : Parcelable