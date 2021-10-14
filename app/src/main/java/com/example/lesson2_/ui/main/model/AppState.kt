package com.example.lesson2_.ui.main.model

sealed class AppState {
    // силд класс интерфейс апстейт с тремя состояниями и параметрами у класса ограниченное количество наследников


    data class Success(val weather : List<Weather>): AppState() // работа с массивом погоды
    data class Error(val weather : Throwable): AppState()
    object Loading : AppState()
}