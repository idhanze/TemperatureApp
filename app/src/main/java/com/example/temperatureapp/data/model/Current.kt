package com.example.temperatureapp.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Current(
    val temp_c: Double? = null,
    val temp_f: Double? = null,
    val condition: Condition? = null
) : Parcelable
