package com.example.lesson2_.ui.main.model.database

import androidx.room.*

@Dao
interface HistoryDao {


    //запрос на SQLitel для получения всех данных из таблицы
    @Query("SELECT * FROM HistoryEntity")
    fun all():List<HistoryEntity>

    //запрос на SQLitel по части слова - LIKE
    @Query("SELECT * FROM HistoryEntity WHERE city LIKE :city")
    fun getDataByWord(city:String):List<HistoryEntity>


    //игнорируем ошибку
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(entity: HistoryEntity)

    //За нас уже все написали, магия, мой будущий труд теряет важность
    @Update
    fun update(entity: HistoryEntity)

    @Delete
    fun delite (entity: HistoryEntity)
}