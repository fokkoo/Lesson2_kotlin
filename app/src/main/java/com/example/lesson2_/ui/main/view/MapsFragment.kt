package com.example.lesson2_.ui.main.view

import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.lesson2_.R
import com.example.lesson2_.ui.main.model.Weather
import com.example.lesson2_.ui.main.model.WeatherLoader
import com.example.lesson2_.ui.main.model.getDefaultCity
import com.example.lesson2_.ui.main.model.getRussianCities

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsFragment : Fragment() {


    companion object {
        const val WEAHTER_EXTRA = "WEAHTER_EXTRA"


        // фабричный метод
        fun newInstance(bundle: Bundle): MapsFragment {
            val fragment = MapsFragment()
            fragment.arguments = bundle
            return fragment
        }
    }


    private val callback = OnMapReadyCallback { googleMap ->

       // compa

        val myWeatherFragmentArgument = arguments?.getParcelable<Weather>(MapsFragment.WEAHTER_EXTRA)
        if(myWeatherFragmentArgument != null) {
            var newLat1 = myWeatherFragmentArgument.city.lat
            var newLong1 = myWeatherFragmentArgument.city.lon


        }


       // val sydney = LatLng(newLat1, newLong1)
        val sydney = LatLng(-10.0, -10.0)
        googleMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)


    }
}