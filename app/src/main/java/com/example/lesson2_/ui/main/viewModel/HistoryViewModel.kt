package com.example.lesson2_.ui.main.viewModel

import androidx.lifecycle.ViewModel
import com.example.lesson2_.ui.main.model.LocalRepositoryImpl
import com.example.lesson2_.ui.main.model.database.HistoryEntity
import com.example.lesson2_.ui.main.view.App

class HistoryViewModel: ViewModel() {

    private val historyRepository = LocalRepositoryImpl(App.getHistoryDao())

   fun  getAllHistory():List<HistoryEntity> = historyRepository.getAllHistory()
}