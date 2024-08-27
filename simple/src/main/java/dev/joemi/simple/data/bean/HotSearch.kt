package dev.joemi.simple.data.bean

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class HotSearch(
    val title: String,
    val heat: String,
    val link: String
) : Parcelable
