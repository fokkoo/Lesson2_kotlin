package com.example.lesson2_.ui.main.model

interface Repository {
    //поставляет погоду
    fun getWetherFromServer(): Weather
    fun getWetherFromLocalStorage(): Weather
}