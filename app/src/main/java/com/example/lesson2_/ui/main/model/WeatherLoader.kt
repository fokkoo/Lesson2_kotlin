package com.example.lesson2_.ui.main.model

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.Exception
import java.net.URL
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection

class WeatherLoader (
    private val lat:Double,
    private val lon:Double,
    private val listner: weatherLoaderListner
        ){
    @RequiresApi(Build.VERSION_CODES.N)
     fun goToInternet() {
        Thread{
            val uri = URL("https://api.weather.yandex.ru/v2/forecast?lat=${lat}&lon=${lon}&lang=ru_Ru")
            var urlConnection: HttpsURLConnection? = null
            try {
                urlConnection = uri.openConnection()as HttpsURLConnection
                urlConnection.apply {
                    requestMethod = "GET"
                    readTimeout = 10000
                    addRequestProperty("X-Yandex-API-Key", "d91d6016-b5ec-4179-8fc6-c5800d6f8c44") // передача ключа разработчика
                }


                val reader = BufferedReader(InputStreamReader(urlConnection.inputStream))
                val result = reader.lines().collect(Collectors.joining("\n"))



                val weatherDTO: WeatherDTO = Gson().fromJson(result,WeatherDTO::class.java)  // магия преобразования джсона в джава объект


                listner.onLoaded(weatherDTO)
               // requireActivity().runOnUiThread {
               //     displayWeather(weatherDTO)
                //}
            } catch (e: Exception) {
                listner.onFailed(e)
                Log.e("", "FAILED", e)
            } finally {
                urlConnection?.disconnect()

            }
        }.start()



    }


    interface weatherLoaderListner {

        fun onLoaded (weatherDTO: WeatherDTO)
        fun onFailed (throwable: Throwable)
    }
}