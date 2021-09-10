package com.example.lesson2_.ui.main.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.lesson2_.R
import com.example.lesson2_.databinding.DetailFragmentBinding
import com.example.lesson2_.databinding.MainFragmentBinding
import com.example.lesson2_.ui.main.viewModel.MainViewModel
import com.example.lesson2_.ui.main.model.AppState
import com.example.lesson2_.ui.main.model.Weather
import com.google.android.material.snackbar.Snackbar

class DetailFragment : Fragment() {

    companion object {
        const val WEAHTER_EXTRA = "WEAHTER_EXTRA"


        // фабричный метод
        fun newInstance(bundle: Bundle) : DetailFragment {
            val fragment = DetailFragment()
            fragment.arguments = bundle
            return fragment
        }
    }



    private  var _binding: DetailFragmentBinding? = null// создание объекта байндинг
    private val binding get()= _binding!! // создание объекта байндинг

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.detail_fragment,container,false)

        _binding = DetailFragmentBinding.bind(view) // инициализация байдинга

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        val weather = arguments?.getParcelable<Weather>(WEAHTER_EXTRA)

        if (weather != null){

            binding.message.text = "${weather.city.name}+ ${weather.city.lat}+${weather.city.lon}+${weather.temperature}"

        }


 //       viewModel = ViewModelProvider(this).get(MainViewModel::class.java) // получение типа класса


// при изменении liveData будет изменяться этот метод
// подписались на данные data: String
//        viewModel.liveData.observe(viewLifecycleOwner){ state ->
   //          renderData(state) }
        // кладем дату в текст по подписке

      //  viewModel.getWeatherFromLocalSource() // вызываем в нужный момент getWeather после подписки
    }


/*
    private fun renderData(state: AppState) {

        when (state){
            is AppState.Loading->binding.loadingLayout.visibility = View.VISIBLE
            is AppState.Success-> {binding.loadingLayout.visibility = View.GONE

               //  binding.message.text = "${state.weather.city.name}+ ${state.weather.city.lat}+${state.weather.city.lon}+${state.weather.temperature}"
            }
            is AppState.Error ->{
                binding.loadingLayout.visibility = View.GONE
            /*    Snackbar
                    .make(binding.mainFragmentFAB,"Error",Snackbar.LENGTH_INDEFINITE)
                    .setAction("reload"){viewModel.getWeatherFromLocalSource()}
                    .show()
*/
            }
        }

      //  binding.message.text = data // замена на приведение к текстВью и поиск по айди
      //  view?.findViewById<TextView>(R.id.message)?.text =data;

/*
        binding.button.setOnClickListener {
            viewModel.getData()
        }*/
    }*/


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // не соберется фрагмент , из за потери данных, из за сылок на элементы  тк фрагмент живет без отображения
    }
}