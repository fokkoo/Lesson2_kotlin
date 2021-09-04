package com.example.lesson2_.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    private  val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData()

    // Mutable значит можно положить туда данные postvalue setvalue view моделей, для активити не мутебл

    val liveData: LiveData <AppState> = liveDataToObserve // лайвдата следящая за состоянием объекта

    fun getWeather() = getDataFromLocalSource()

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
            liveDataToObserve.postValue(AppState.Success("nice weather"))


        }.start()
    }

}