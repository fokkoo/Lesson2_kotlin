package com.example.lesson2_.ui.main.model

class RepositoryImpl : Repository {
    override fun getWetherFromServer(): Weather = Weather()
    override fun getWetherFromLocalStorageWorld(): List<Weather> = getWorldCities()

    override fun getWetherFromLocalStorageRus(): List<Weather> = getRussianCities()

}