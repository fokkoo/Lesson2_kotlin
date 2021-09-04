package com.example.lesson2_.ui.main

sealed class AppState {
    // силд класс интерфейс апстейт с тремя состояниями и параметрами у класса ограниченное количество наследников


    data class Success(val weather : String): AppState()
    data class Error(val weather : Throwable): AppState()
    object Loading : AppState()
}