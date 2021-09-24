package com.example.lesson2_.ui.main.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.lesson2_.R
import com.example.lesson2_.databinding.DetailFragmentBinding
import com.example.lesson2_.databinding.FragmentHistoryBinding
import com.example.lesson2_.databinding.MainFragmentBinding
import com.example.lesson2_.ui.main.viewModel.HistoryViewModel


class HistoryFragment : Fragment() {

    private val viewModel: HistoryViewModel by lazy {
        ViewModelProvider(this).get(HistoryViewModel::class.java)
    }


    private var _binding: FragmentHistoryBinding? = null// создание объекта байндинг
    private val binding get() = _binding!! // создание объекта байндинг


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_history, container, false)

        _binding = FragmentHistoryBinding.bind(view) // инициализация байдинга


        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding =
            null // не соберется фрагмент , из за потери данных, из за сылок на элементы  тк фрагмент живет без отображения
    }
}
