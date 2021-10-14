package com.example.lesson2_.ui.main.model.database

import androidx.room.Database
import androidx.room.RoomDatabase


// :: получит меетаданные о классе то есть его имя

// указываем количество таблиц, версии чтобы не посыпались работающие программы у пользователей при обновлении новых идей манагера, миграция с 1 до Н
// описываются все три компонента Dao database Entity
@Database(entities = [HistoryEntity::class],version = 1,exportSchema = true)
abstract class HistoryDataBase: RoomDatabase() {


    abstract fun historyDao(): HistoryDao
}