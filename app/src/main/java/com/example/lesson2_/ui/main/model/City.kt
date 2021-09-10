package com.example.lesson2_.ui.main.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

// @Parcelize создает методы при наличии в bield.gradel id 'kotlin-android-extensions' не видеть методы которые написаны
@Parcelize
data class City(
    val name: String,
    val lat: Double,
    val lon: Double
):Parcelable