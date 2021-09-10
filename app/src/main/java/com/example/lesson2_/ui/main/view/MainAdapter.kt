package com.example.lesson2_.ui.main.view

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

// создаем ресайклер вью путем наследования от него

class MainAdapter : RecyclerView.Adapter<MainAdapter.MainViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    inner class MainViewHolder(view: View) : RecyclerView.ViewHolder(view){

    }
}