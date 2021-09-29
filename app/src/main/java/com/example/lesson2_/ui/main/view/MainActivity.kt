package com.example.lesson2_.ui.main.view

import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import com.example.lesson2_.R
import com.example.lesson2_.databinding.MainActivityBinding
import kotlin.time.measureTimedValue

class MainActivity : AppCompatActivity() {

    private lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        binding = MainActivityBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root) // передача R.layout.main_activity 1.55


        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commitNow()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.idHistory ->{
                supportFragmentManager.apply {
                    beginTransaction()
                        .replace(R.id.container,HistoryFragment())
                        .addToBackStack("")
                        .commitAllowingStateLoss()
                }
                true
            }

            // обработка нажатия кнопки запроса контактов и вызов соответствующего метода
            R.id.getContacts ->{
                getContact()
                true
            }


            else ->super.onOptionsItemSelected(item)




        }


    }

    private fun getContact() {
        // обращение к контент провайдеру контактов
            contentResolver
        // обращение к курсору по элементного считывания контактов
        val cursor: Cursor?=contentResolver.query(
            ContactsContract.Contacts.CONTENT_URI,
            null,
            null,
            null,
            ContactsContract.Contacts.DISPLAY_NAME+" ASC"
        )

        val contacts = mutableListOf<String>()

        cursor?.let{ cursor ->
            for (i in 0..cursor.count) {
                // Переходим на позицию в Cursor
                if (cursor.moveToPosition(i)) {
                    // Берём из Cursor столбец с именем
                    val name =
                        cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))

                    // складываем имена в массив
                    contacts.add(name)
                }
            }

        }
        AlertDialog.Builder(this)
            .setItems(contacts.toTypedArray(), {d,w->})
            .setCancelable(true)
            .show()
    }


}