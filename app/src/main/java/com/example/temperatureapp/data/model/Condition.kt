package com.example.temperatureapp.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Condition(
    val text: String? = null,
    val icon: String? = null
) : Parcelable