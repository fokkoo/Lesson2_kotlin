package com.example.lesson2_.ui.main.view

import android.os.Build
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.lesson2_.R
import com.example.lesson2_.databinding.DetailFragmentBinding
import com.example.lesson2_.databinding.MainFragmentBinding
import com.example.lesson2_.ui.main.model.*
import com.example.lesson2_.ui.main.viewModel.MainViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import kotlinx.android.synthetic.main.detail_fragment.view.*
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.Exception
import java.net.URL
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection

class DetailFragment : Fragment() {

    companion object {
        const val WEAHTER_EXTRA = "WEAHTER_EXTRA"


        // фабричный метод
        fun newInstance(bundle: Bundle): DetailFragment {
            val fragment = DetailFragment()
            fragment.arguments = bundle
            return fragment
        }
    }


    private var _binding: DetailFragmentBinding? = null// создание объекта байндинг
    private val binding get() = _binding!! // создание объекта байндинг

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.detail_fragment, container, false)

        _binding = DetailFragmentBinding.bind(view) // инициализация байдинга

        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        // ?.let проверка на null
        arguments?.getParcelable<Weather>(WEAHTER_EXTRA)?.let { weather ->

            weather.city.also { city ->
                binding.city.text =
                    city.name // binding - объект который связывает айди макета с активити
                binding.lat.text = city.lat.toString()
                binding.lon.text = city.lon.toString()


            }

            /*
            with (binding){
                temperature.text = weather.temperature.toString()
            }*/

            WeatherLoader(
                weather.city.lat,
                weather.city.lon,
                object : WeatherLoader.weatherLoaderListner{
                    override fun onLoaded(weatherDTO: WeatherDTO) {
                        requireActivity().runOnUiThread{
                        displayWeather(weatherDTO)
                        }
                    }

                    override fun onFailed(throwable: Throwable) {

                        requireActivity().runOnUiThread{
                            Toast.makeText(requireContext(),throwable.localizedMessage,Toast.LENGTH_LONG).show()
                        }

                    }
                }

            ).goToInternet()

        }


    //    if (weather != null) {
     //       binding.city.text =
    //            weather.city.name // binding - объект который связывает айди макета с активити
    //        binding.lat.text = weather.city.lat.toString()

    //        binding.message.text =
     //           "${weather.city.name}+ ${weather.city.lat}+${weather.city.lon}+${weather.temperature}"

     //   }


        //       viewModel = ViewModelProvider(this).get(MainViewModel::class.java) // получение типа класса


// при изменении liveData будет изменяться этот метод
// подписались на данные data: String
//        viewModel.liveData.observe(viewLifecycleOwner){ state ->
        //          renderData(state) }
        // кладем дату в текст по подписке

        //  viewModel.getWeatherFromLocalSource() // вызываем в нужный момент getWeather после подписки



    }


    fun displayWeather(weather: WeatherDTO) {

        with (binding){

            temperature.text = weather.fact?.feels_like.toString()
        }

    }



  /*  fun displayWeather(weather: WeatherDTO, city: City) {
        city.also { city ->
            binding.city.text = city.name
            binding.lat.text = city.lat.toString()
            binding.lon.text = city.lon.toString()

            //  binding.city.lat.text = "${city.lat}-${city.lon}"
        }
        with (binding){

            temperature.text = weather.fact?.feels_like.toString()
        }

    }*/


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
        _binding =
            null // не соберется фрагмент , из за потери данных, из за сылок на элементы  тк фрагмент живет без отображения
    }
}