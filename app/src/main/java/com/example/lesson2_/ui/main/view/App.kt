package com.example.lesson2_.ui.main.view

import android.app.Application
import android.util.Log
import androidx.room.Room
import com.example.lesson2_.ui.main.model.database.HistoryDao
import com.example.lesson2_.ui.main.model.database.HistoryDataBase
import com.google.firebase.messaging.FirebaseMessaging
import java.lang.IllegalStateException


// не забыть прописать в манифесте
class App: Application() {

    override fun onCreate() {
        super.onCreate()
        // приложение сложили в переменную и можем обратиться
        appInstance = this

        FirebaseMessaging.getInstance().token.addOnCompleteListener {task->
            if(task.isSuccessful){
                task.result.toString()
                Log.d("MyFMessagingService","token=${task.result.toString()}")
            }

        }
    }

    companion object{
        private var appInstance: App? = null
        private var db: HistoryDataBase? = null

        //имя будущего файда базы данных
        private const val DB_NAME = "History.db"

        // метод для получения базы данных

        fun getHistoryDao(): HistoryDao{
            if (db == null){
                // первичная инициализаци стандартного приема
                if (appInstance == null) throw IllegalStateException("WTF?")

                db = Room.databaseBuilder(
                    appInstance!!,
                    HistoryDataBase::class.java,
                    DB_NAME
                ).allowMainThreadQueries()
                    .build()
                //поменять allowMainThreadQueries() на отдельный поток
                //
            }
            return db!!.historyDao()
        }
    }
}