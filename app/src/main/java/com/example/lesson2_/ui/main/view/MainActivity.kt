package com.example.lesson2_.ui.main.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.lesson2_.R
import com.example.lesson2_.databinding.MainActivityBinding

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
}