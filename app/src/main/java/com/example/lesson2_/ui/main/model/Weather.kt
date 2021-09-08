package com.example.lesson2_.ui.main.model

data class Weather (
    val city: City = getDefaultCity(),
    val temperature: Int = 0,
    val feelsLike: Int = 0
        ){


}

fun getDefaultCity(): City = City("Moscow",55.75,37.61)

