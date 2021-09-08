package com.example.lesson2_.ui.main

class RepositoryImpl : Repository {
    override fun getWetherFromServer(): Weather = Weather()
    override fun getWetherFromLocalStorage(): Weather = Weather()

}