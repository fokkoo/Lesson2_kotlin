package com.example.lesson2_.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {



    private  val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData()

    private  val repository: Repository = RepositoryImpl()

    // Mutable значит можно положить туда данные postvalue setvalue view моделей, для активити не мутебл

    val liveData: LiveData <AppState> = liveDataToObserve // лайвдата следящая за состоянием объекта

    fun getWeatherFromLocalSource() = getDataFromLocalSource()
    fun getWeatherFromRemoteSource() = getDataFromLocalSource()

    /*
    fun getData(): LiveData<String> {

        getDataFromLocalSource()

        return liveDataToObserve // кастинг
    }

     */

    private fun getDataFromLocalSource (){
        liveDataToObserve.value = AppState.Loading// как только дернули то первым начинаем загрузку

        Thread{
            Thread.sleep(5000)
            liveDataToObserve.postValue(AppState.Success(repository.getWetherFromLocalStorage()))


        }.start()
    }

}