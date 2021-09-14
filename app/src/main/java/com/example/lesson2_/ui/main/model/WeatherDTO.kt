package com.example.lesson2_.ui.main.model

//объект полученный из интернета data transfer object

data class WeatherDTO(
    val fact: FactDTO?
    
) {

    data class FactDTO(

        val temp: Int?, // название переменных точно так же как в json чтобы запрос сработал
        val feels_like:Int?,
        val condition: String?
    )

}