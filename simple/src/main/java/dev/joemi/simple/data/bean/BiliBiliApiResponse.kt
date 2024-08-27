package dev.joemi.simple.data.bean

import android.os.Parcelable

import kotlinx.parcelize.Parcelize

@Parcelize
data class BiliBiliApiResponse(
    val code: Int,
    val data: List<HotSearch>?,
    val msg: String,
    val time: String
) : Parcelable