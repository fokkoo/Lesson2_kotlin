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
import com.example.lesson2_.databinding.MainFragmentBinding

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    private  var _binding: MainFragmentBinding? = null// создание объекта байндинг
    private val binding get()= _binding!! // создание объекта байндинг

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.main_fragment,container,false)

        _binding = MainFragmentBinding.bind(view) // инициализация байдинга

        return binding.root
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

        binding.message.text = data // замена на приведение к текстВью и поиск по айди
      //  view?.findViewById<TextView>(R.id.message)?.text =data;


        binding.button.setOnClickListener {
            viewModel.getData()
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // не соберется фрагмент , из за потери данных, из за сылок на элементы  тк фрагмент живет без отображения
    }
}