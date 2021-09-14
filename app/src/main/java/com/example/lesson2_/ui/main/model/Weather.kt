package com.example.lesson2_.ui.main.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


// @Parcelize создает методы при наличии в bield.gradel id 'kotlin-android-extensions'
@Parcelize
data class Weather (
    val city: City = getDefaultCity(),
    val temperature: Int = 0,
    val feelsLike: Int = 0
        ): Parcelable{


}

fun getDefaultCity(): City = City("Moscow",55.75,37.61)

// в интерфейсе менять не будем поэтому не мутбл
fun getWorldCities(): List <Weather> = listOf(
    Weather(City("London",51.5,-0.12),1,1),
    Weather(City("Tokio",35.5,139.12),2,2),
)

fun getRussianCities(): List <Weather> = listOf(
    Weather(City("Moscow",55.7,37.12),-5,6),
    Weather(City("Kazan",55.8,49.12),5,5),
)