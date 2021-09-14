package com.example.lesson2_.ui.main.model

interface Repository {
    //поставляет погоду
    fun getWetherFromServer(): Weather
    fun getWetherFromLocalStorageRus(): List<Weather>
    fun getWetherFromLocalStorageWorld(): List<Weather>
}