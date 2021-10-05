package com.example.lesson2_.ui.main.view

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import com.example.lesson2_.R
import com.example.lesson2_.databinding.MainActivityBinding
import java.io.IOException

import kotlin.time.measureTimedValue

private const val REFRESH_PERIOD = 60000L // частота запроса координат
private const val MINIMAL_DISTANCE = 100f // минимальный шаг пользователя

class MainActivity : AppCompatActivity() {


    // запрос разрешения на контакты и реакция на запрос
    @RequiresApi(Build.VERSION_CODES.M)
    private val permissionResult =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { result ->
            when {
                //granded
                result -> getContact()

                // метод, который отслеживает галку пользователя о запрете повторного показа предложения
                !shouldShowRequestPermissionRationale(Manifest.permission.READ_CONTACTS) -> {
                    Toast.makeText(
                        this,
                        "Go to app settings and give permission",
                        Toast.LENGTH_LONG
                    ).show()
                }
                //denied
                else -> Toast.makeText(this, "no permition", Toast.LENGTH_LONG).show()
            }


        }

    // запрос разрешения на геолокацию и реакция на запрос
    @RequiresApi(Build.VERSION_CODES.M)
    private val permissionGeoResult =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { result ->
            when {
                //granded
                result -> getLocation()

                // метод, который отслеживает галку пользователя о запрете повторного показа предложения
                !shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION) -> {
                    Toast.makeText(
                        this,
                        "Go to app settings and give permission",
                        Toast.LENGTH_LONG
                    ).show()
                }
                //denied
                else -> Toast.makeText(this, "no permition", Toast.LENGTH_LONG).show()
            }


        }

    @SuppressLint("MissingPermission")
    private fun getLocation() {
        val locationManager = this.getSystemService(Context.LOCATION_SERVICE) as LocationManager

// проверяем включен ли GPS
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            // запрос точных данных в GPS_PROVIDER/ let - внутри не будет nullble
            locationManager.getProvider(LocationManager.GPS_PROVIDER)?.let {
                locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    REFRESH_PERIOD,
                    MINIMAL_DISTANCE,
                    object: LocationListener{
                        override fun onLocationChanged(location: Location) {
                            getAddressByLocation(location)
                        }

                        override fun onStatusChanged(
                            provider: String?,
                            status: Int,
                            extras: Bundle?
                        ) {
                         // метод устарел, поэтому его нужно вырезать. Это подводный камень новичка   super.onStatusChanged(provider, status, extras)
                        }


                    }
                )
            }
        } else{
            // получаем последнюю известную координату пользователя здесь продолжить добавлять проверки по вайфай и др условия
                val location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)

            if (location == null){
                // give massege to user - no GPS
            } else {
                getAddressByLocation(location)
            }
        }
    }

    private fun getAddressByLocation(location: Location) {
        val geocoder = Geocoder(this) // геокодер предоставляет координаты, но ходит в интернет, поэтому идет в отдельном потоке
        Thread{
            try {
                val address = geocoder.getFromLocation(
                    location.latitude,
                    location.longitude,
                       1
                )

// передаем через пост потому как мы находимся в неосновном потоке
                binding.container.post {
                  //  binding.container.showSbackBar(address[0].getAddressLine(0))
                    AlertDialog.Builder(this).setMessage(address[0].getAddressLine(0))
                        .setCancelable(true)
                        .show()
                }





            }catch(e:IOException){
                e.printStackTrace()
            }
        }.start()
    }


    private lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        binding = MainActivityBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root) // передача R.layout.main_activity 1.55


        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.idHistory -> {
                supportFragmentManager.apply {
                    beginTransaction()
                        .replace(R.id.container, HistoryFragment())
                        .addToBackStack("")
                        .commitAllowingStateLoss()
                }
                true
            }

            // обработка нажатия кнопки запроса контактов и вызов соответствующего метода
            R.id.getContacts -> {
                permissionResult.launch(Manifest.permission.READ_CONTACTS)

                true
            }
            // обработка нажатия кнопки запроса контактов и вызов соответствующего метода
            R.id.getLocation -> {
                permissionGeoResult.launch(Manifest.permission.ACCESS_FINE_LOCATION)

                true
            }

            else -> super.onOptionsItemSelected(item)


        }


    }

    private fun getContact() {
        // обращение к контент провайдеру контактов
        contentResolver
        // обращение к курсору по элементного считывания контактов
        val cursor: Cursor? = contentResolver.query(
            ContactsContract.Contacts.CONTENT_URI,
            null,
            null,
            null,
            ContactsContract.Contacts.DISPLAY_NAME + " ASC"

        )


        val contacts = mutableListOf<String>()
        val contactsNumb = mutableListOf<String>()

        cursor?.let { cursor ->
            for (i in 0..cursor.count) {
                // Переходим на позицию в Cursor
                if (cursor.moveToPosition(i)) {
                    // Берём из Cursor столбец с именем
                    val name =
                        cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                    // val name =  cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.SEARCH_PHONE_NUMBER_KEY))
                    //  val telephoneNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                    // складываем имена в массив
                    contacts.add(name)
                    //  contacts.add(telephoneNumber)
                    //   contactsNumb.add(telephoneNumber)

                    // складываем телефоны в массив
                    //    contactsNumb.add(telephoneNumber.toString())
                }
            }

        }
        AlertDialog.Builder(this)
            .setItems(contacts.toTypedArray(), { d, w -> })
            .setCancelable(true)
            .show()
    }


}