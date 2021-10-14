package com.example.lesson2_.ui.main.model.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class HistoryEntity (
    @PrimaryKey(autoGenerate = true)
    val id:Long,
    val city:String,
    val temerature: Int,
    val condition: String,
    val timestamp: Long,

    // файл базы данных с ключем автогенерации и основными заголовками данных
        )