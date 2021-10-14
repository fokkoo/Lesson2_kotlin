package com.example.lesson2_.ui.main.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lesson2_.R
import com.example.lesson2_.ui.main.model.Weather

// создаем ресайклер вью путем наследования от него

class MainAdapter : RecyclerView.Adapter<MainAdapter.MainViewHolder>() {


    var weatherData: List<Weather> = listOf()
        set(value) {


            field = value
            notifyDataSetChanged() // для отображения не забыть
        }
    var listener: OnItemViewClickListner? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder =
        MainViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.main_fragment_item, parent, false)
        )

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(weatherData[position])
    }

    override fun getItemCount(): Int = weatherData.size

    inner class MainViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(weather: Weather) {
            itemView.findViewById<TextView>(R.id.cityName).text = weather.city.name

           //когда кликают в элемент списка то мы вызываем через безопасный вызов функцию и передаем ей погоду
            itemView.setOnClickListener {
                listener?.onItemClick(weather)
            }
        }
    }



    // интерфейс внутри адаптера функциональный
  fun  interface OnItemViewClickListner {
        fun onItemClick(weather: Weather)
    }
}