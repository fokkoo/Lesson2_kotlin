package com.example.lesson2_.ui.main.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.lesson2_.R
import com.example.lesson2_.databinding.MainFragmentBinding
import com.example.lesson2_.ui.main.viewModel.MainViewModel
import com.example.lesson2_.ui.main.model.AppState
import com.example.lesson2_.ui.main.model.Weather
import com.google.android.material.snackbar.Snackbar

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    private var _binding: MainFragmentBinding? = null// создание объекта байндинг
    private val binding get() = _binding!! // создание объекта байндинг

    private lateinit var adapter: MainAdapter
    private var isRus: Boolean = true


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.main_fragment, container, false)

        _binding = MainFragmentBinding.bind(view) // инициализация байдинга


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = MainAdapter()
        adapter.listener = object:MainAdapter.OnItemViewClickListner{
            override fun onItemClick(weather: Weather) {
                val manager = activity?.supportFragmentManager
                if (manager != null){
                    val bundle = Bundle()
                    // создали бандл и положили в него погоду и передали во фрагмент
                    bundle.putParcelable(DetailFragment.WEAHTER_EXTRA,weather)
                    manager.beginTransaction()
                        .replace(R.id.container,DetailFragment.newInstance(bundle))
                        .addToBackStack("")
                        .commit()
                }
            }

        }

        binding.recyclerView.adapter = adapter
        binding.mainFragmentFAB.setOnClickListener {
            isRus = ! isRus // меняем метку флага
            if (isRus){
                //перезапрашиваем данные

                binding.mainFragmentFAB.setImageResource(R.drawable.ic_russian)
            }else{
                binding.mainFragmentFAB.setImageResource(R.drawable.ic_world)
            }
            viewModel.getWeatherFromLocalSource(isRus)
        }


        viewModel = ViewModelProvider(this).get(MainViewModel::class.java) // получение типа класса


// при изменении liveData будет изменяться этот метод
// подписались на данные data: String
        viewModel.liveData.observe(viewLifecycleOwner) { state ->
            renderData(state)
        }
        // кладем дату в текст по подписке

        viewModel.getWeatherFromLocalSource(isRus) // вызываем в нужный момент getWeather после подписки
    }

    private fun renderData(state: AppState) {

        when (state) {
            is AppState.Loading -> binding.loadingLayout.visibility = View.VISIBLE
            is AppState.Success -> {
                binding.loadingLayout.visibility = View.GONE
                adapter.weatherData = state.weather

                viewModel.saveWeather(state.weather.first())
            }
            is AppState.Error -> {
                binding.loadingLayout.visibility = View.GONE
                Snackbar
                     .make(binding.mainFragmentFAB,"Error",Snackbar.LENGTH_INDEFINITE)
                     .setAction("reload"){viewModel.getWeatherFromLocalSource(isRus)}
                     .show()

            }
        }

        //  binding.message.text = data // замена на приведение к текстВью и поиск по айди
        //  view?.findViewById<TextView>(R.id.message)?.text =data;

/*
        binding.button.setOnClickListener {
            viewModel.getData()
        }*/
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding =
            null // не соберется фрагмент , из за потери данных, из за сылок на элементы  тк фрагмент живет без отображения
    }
}