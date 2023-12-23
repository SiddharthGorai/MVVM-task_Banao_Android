package com.example.mvvmtask.Data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class photoData(
    val photos: Photos,
    val stat: String
) : Parcelable