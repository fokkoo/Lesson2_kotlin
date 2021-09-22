package com.example.lesson2_.ui.main.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lesson2_.ui.main.model.*
import com.example.lesson2_.ui.main.model.database.HistoryEntity
import com.example.lesson2_.ui.main.view.App
import java.util.*

class MainViewModel : ViewModel() {


    private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData()

    private val repository: Repository = RepositoryImpl()

    private val localRepository:LocalRepository = LocalRepositoryImpl(App.getHistoryDao())

    // Mutable значит можно положить туда данные postvalue setvalue view моделей, для активити не мутебл

    val liveData: LiveData<AppState> = liveDataToObserve // лайвдата следящая за состоянием объекта

    fun getWeatherFromLocalSource(isRussian:Boolean = true) = getDataFromLocalSource(isRussian)
    fun getWeatherFromRemoteSource() = getDataFromLocalSource()

    /*
    fun getData(): LiveData<String> {

        getDataFromLocalSource()

        return liveDataToObserve // кастинг
    }

     */

    private fun getDataFromLocalSource(isRussian: Boolean = true) {
        liveDataToObserve.value = AppState.Loading// как только дернули то первым начинаем загрузку

        Thread {
            Thread.sleep(500)
            liveDataToObserve.postValue(
                AppState.Success(
                    if (isRussian) {
                        repository.getWetherFromLocalStorageRus()
                    } else {
                        repository.getWetherFromLocalStorageWorld()

                    }
                )
            )


        }.start()
    }

    fun saveWeather(weather: Weather){
        localRepository.saveEntity(
            HistoryEntity(
                id=0,
                city= weather.city.name,
                temerature = weather.temperature,
                condition="Norm condition, to do",
                timestamp = Date().time
            )
        )

    }
}