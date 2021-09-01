package com.example.lesson2_.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    private  val liveDataToObserve: MutableLiveData<String> = MutableLiveData()

    // Mutable значит можно положить туда данные postvalue setvalue view моделей, для активити не мутебл

    fun getData(): LiveData<String> {

        getDataFromLocalSource()

        return liveDataToObserve // кастинг
    }

    private fun getDataFromLocalSource (){
        Thread{
            Thread.sleep(5000)
            liveDataToObserve.postValue("данные 1")
            Thread.sleep(5000)
            liveDataToObserve.postValue("данные 2")
            Thread.sleep(5000)
            liveDataToObserve.postValue("данные 3")

        }.start()
    }

}