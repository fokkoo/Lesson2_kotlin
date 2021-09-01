package com.example.lesson2_.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import com.example.lesson2_.R

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java) // получение типа класса


// при изменении liveData будет изменяться этот метод
// подписались на данные data: String
        viewModel.getData().observe(viewLifecycleOwner){ data : String -> renderData(data) }
        // кладем дату в текст по подписке
    }

    private fun renderData(data: String?) {
        view?.findViewById<TextView>(R.id.message)?.text =data;
    }



}