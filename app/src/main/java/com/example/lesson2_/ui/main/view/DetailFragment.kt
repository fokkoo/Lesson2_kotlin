package com.example.lesson2_.ui.main.view

import android.os.Build
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import com.example.lesson2_.R
import com.example.lesson2_.databinding.DetailFragmentBinding
import com.example.lesson2_.databinding.MainFragmentBinding
import com.example.lesson2_.ui.main.viewModel.MainViewModel
import com.example.lesson2_.ui.main.model.AppState
import com.example.lesson2_.ui.main.model.City
import com.example.lesson2_.ui.main.model.Weather
import com.example.lesson2_.ui.main.model.WeatherDTO
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

            Thread{
                goToInternet(
                    uri =  URL("https://api.weather.yandex.ru/v2/forecast?lat=${weather.city.lat}&lon=${weather.city.lon}&lang=ru_Ru")
                )
            }.start()

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

    @RequiresApi(Build.VERSION_CODES.N)
    private fun goToInternet(uri: URL) {
        var urlConnection: HttpsURLConnection? = null
        try {
            urlConnection = uri.openConnection() as HttpsURLConnection
            urlConnection.apply {
                requestMethod = "GET"
                readTimeout = 10000
                addRequestProperty("X-Yandex-API-Key", "d91d6016-b5ec-4179-8fc6-c5800d6f8c44") // передача ключа разработчика
            }


            val reader = BufferedReader(InputStreamReader(urlConnection.inputStream))
            val result = reader.lines().collect(Collectors.joining("\n"))



            val weatherDTO: WeatherDTO = Gson().fromJson(result,WeatherDTO::class.java)  // магия преобразования джсона в джава объект

             requireActivity().runOnUiThread {
               displayWeather(weatherDTO)
             }
        } catch (e: Exception) {
            Log.e("", "FAILED", e)
        } finally {
            urlConnection?.disconnect()

        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding =
            null // не соберется фрагмент , из за потери данных, из за сылок на элементы  тк фрагмент живет без отображения
    }
}