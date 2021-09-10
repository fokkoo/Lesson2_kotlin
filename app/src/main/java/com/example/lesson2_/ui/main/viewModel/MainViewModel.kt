package com.example.lesson2_.ui.main.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lesson2_.ui.main.model.AppState
import com.example.lesson2_.ui.main.model.Repository
import com.example.lesson2_.ui.main.model.RepositoryImpl

class MainViewModel : ViewModel() {


    private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData()

    private val repository: Repository = RepositoryImpl()

    // Mutable значит можно положить туда данные postvalue setvalue view моделей, для активити не мутебл

    val liveData: LiveData<AppState> = liveDataToObserve // лайвдата следящая за состоянием объекта

    fun getWeatherFromLocalSource() = getDataFromLocalSource()
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
            Thread.sleep(2000)
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

}