package com.example.lesson2_.ui.main

interface Repository {
    //поставляет погоду
    fun getWetherFromServer(): Weather
    fun getWetherFromLocalStorage(): Weather
}