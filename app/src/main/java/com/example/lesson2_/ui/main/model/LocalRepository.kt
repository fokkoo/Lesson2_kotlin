package com.example.lesson2_.ui.main.model

import com.example.lesson2_.ui.main.model.database.HistoryEntity

interface LocalRepository {
    fun  getAllHistory():List<HistoryEntity>
    fun saveEntity(weather: HistoryEntity)
}