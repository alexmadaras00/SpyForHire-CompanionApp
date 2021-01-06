package com.example.spyforhire

import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.location.*
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.fragment_missions_screen.*
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.lang.Thread.sleep
import kotlin.properties.Delegates


const val EXTRA_USER_MAP="EXTRA_USER_MAP"
const val TAG="Mission"

class MissionsScreen : Fragment(R.layout.fragment_missions_screen), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {
    private val REQUEST_LOCATION_PERMISSION = 1

    lateinit var mMap: GoogleMap
    lateinit var userMap: CardView
    lateinit var locationRequest: LocationRequest
    lateinit var locationCallback: LocationCallback
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    lateinit var mLastLocation: Location
    lateinit var mMarker: Marker
    var latitude by Delegates.notNull<Double>()
    var longitude by Delegates.notNull<Double>()

    companion object {
        private val MY_PERMISSION_CODE: Int = 1000
    }


    var changed: Boolean = false
    var created: Boolean? = false
    var endTimer: Long? = 0
    val time: Long = 5L
    var sec = 3600L
    var timeStart = time * 1000L
    var START_MILLI_SECONDS = sec * 1000L
    var k: Int = 0
    lateinit var countdown_timer: CountDownTimer
    var isRunning: Boolean = false
    var itList = ArrayList<CardView>()
    private var layoutManager: RecyclerView.LayoutManager? = null
    var complete: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        val mapFragment=activity?.supportFragmentManager?.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        if (complete == false) {
            itList.add(
                CardView(
                    "Go to 25th of April bridge, take a photo of it and then write when it was built and by who.",
                    100,
                    50,
                    R.drawable.monetization_on_24px,
                    0,
                    R.id.bar1,
                    false,
                    -1.23,
                    0.23
                )
            )
            itList.add(
                CardView(
                    "Go to Praca do Comercio, take a photo of it and then write when it was built and by who.",
                    250,
                    50,
                    R.drawable.monetization_on_24px,
                    0,
                    R.id.bar1,
                    false,
                    -1.23,
                    0.23
                )
            )
            itList.add(
                CardView(
                    "Go to Marques de Pombal, take a photo of it and then write when it was built and by who.",
                    300,
                    50,
                    R.drawable.monetization_on_24px,
                    0,
                    R.id.bar1,
                    false,
                    -1.23,
                    0.23
                )
            )
            itList.add(
                CardView(
                    "Go to Rossio , take a photo of it and then write when it was built and by who.",
                    400,
                    50,
                    R.drawable.monetization_on_24px,
                    0,
                    R.id.bar1,
                    false,
                    -1.23,
                    0.23
                )
            )
            complete = true
        }
        if (complete == true) {
            itList[0]
            itList[1]
            itList[2]
            itList[3]
        }

            checkLocationPermission()
            buildLocationRequest()
            buildLocationCallBack()
            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this.requireActivity())
            fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper())

            checkLocationPermission()
            buildLocationRequest()
            buildLocationCallBack()
            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this.requireActivity())
            fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper())

        super.onCreate(savedInstanceState)


    }


    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {


        super.onViewCreated(itemView, savedInstanceState)
        startTimer(time_in_seconds)

        activity?.let {
            Log.i(TAG, "$sec")
            view?.findViewById<ImageView>(R.id.settings2)?.setOnClickListener {
                val intent = Intent(activity, Settings::class.java)
                intent.putExtra("time", timeStart)
                startActivity(intent)
            }
            view?.findViewById<ImageView>(R.id.trophy2)?.setOnClickListener {
                val intent = Intent(activity, Achievements::class.java)
                startActivity(intent)
            }

            val recyclerView = view?.findViewById<RecyclerView>(R.id.recyclerView)
            if (recyclerView != null) {
                recyclerView.adapter = Adapter(itList, object : Adapter.OnClickListener {
                    override fun onItemClick(position: Int) {
                        Log.i(TAG, "mission $position")
                        val intent = Intent(activity, MapsActivity::class.java)
                        intent.putExtra(EXTRA_USER_MAP, itList[position])

                        startActivity(intent)
                    }

                })
            }
            if (recyclerView != null) {
                recyclerView.layoutManager = LinearLayoutManager(this.activity)
            }


        }
        this.activity?.let { LocationServices.getFusedLocationProviderClient(it) }



    }

    var time_in_seconds = timeStart
    fun startTimer(time: Long) {
        endTimer = System.currentTimeMillis() + time_in_seconds
        countdown_timer = object : CountDownTimer(time, 1000) {

            override fun onFinish() {
                startTimer(START_MILLI_SECONDS)

            }

            override fun onTick(p0: Long) {
                time_in_seconds = p0

                updateTextUI()
            }
        }



        countdown_timer.start()

        isRunning = true
    }

    private fun pauseTimer() {
        countdown_timer.cancel()
        isRunning = false
        updateTextUI()
    }

    private fun updateTextUI() {
        val seconds: Long = (((time_in_seconds) / 1000)) % 60
        val minutes = ((time_in_seconds) / 1000 / 60) % 60
        val hours: Long = ((time_in_seconds) / (1000 * 60 * 60)) % 24
        val days: Long = ((time_in_seconds) / (1000 * 60 * 60 * 24))


        if (days > 0L && hours > 0L) {
            (days).toString().also { expiration_time_days?.text = it }
            (hours).toString().also { expiration_time_hours?.text = it }

        } else {
            if (days == 0L) {
                (hours).toString().also { expiration_time_days?.text = it }
                (minutes).toString().also { expiration_time_hours?.text = it }
                expiration_time_unit1?.text = "h"
                expiration_time_unit2?.text = "m"
                if (hours == 0L) {
                    (minutes).toString().also { expiration_time_days?.text = it }
                    (seconds).toString().also { expiration_time_hours?.text = it }
                    expiration_time_unit1?.text = "m"
                    expiration_time_unit2?.text = "s"
                    if (expiration_time_hours?.text?.toString()
                            ?.toInt() == 3 && expiration_time_days?.text?.toString()
                            ?.toInt() == 0 && expiration_time_unit2?.text?.toString() == "s"
                    ) {
                        val mp = MediaPlayer.create(activity, R.raw.countdown)
                        mp.start()
                        mp.setVolume(1f, 100f)
                    }
                    if (expiration_time_hours?.text?.toString()?.toLong() == 0L) {
                        val mp = MediaPlayer.create(activity, R.raw.countdown)
                        sleep(1000)
                        mp.stop()

                    }

                    if (minutes == 0L && seconds == 0L) {
                        expiration_time_unit1?.text = "d"
                        expiration_time_unit2?.text = "h"
                        (minutes).toString().also { expiration_time_days?.text = it }
                        (seconds).toString().also { expiration_time_hours?.text = it }
                    }

                }
            }

        }

    }

    private fun resetTimer() {
        time_in_seconds = START_MILLI_SECONDS
        updateTextUI()

    }

    fun makeApiCall(location: GoogleMap?) {
        val request = Request.Builder()
            .url("https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=${location?.myLocation?.latitude},${location?.myLocation?.longitude}&radius=1500&type=restaurant&key=AIzaSyDlFa7HmCTV75yB-pxsitXu4c2k8TA23xA")
            .build()

        val response = OkHttpClient().newCall(request).execute().body?.string()
        val jsonObject = JSONObject(response) // This will make the json below as an object for you

        // You can access all the attributes , nested ones using JSONArray and JSONObject here


    }

    private fun buildLocationCallBack() {
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(p0: LocationResult?) {
                mLastLocation = p0!!.locations.get(p0.locations.size - 1)
                latitude = mLastLocation.latitude
                longitude = mLastLocation.longitude
                val latLng = LatLng(latitude, longitude)
                val markerOptions = MarkerOptions().position(latLng).title("Your position")
                mMarker = mMap.addMarker(markerOptions)
                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng))
                mMap.animateCamera(CameraUpdateFactory.zoomBy(11f))
            }
        }
    }

    private fun buildLocationRequest() {
        locationRequest = LocationRequest()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 5000
        locationRequest.fastestInterval = 3000
        locationRequest.smallestDisplacement = 10f
    }

    private fun checkLocationPermission():Boolean {
        if (this.activity?.let {
                ContextCompat.checkSelfPermission(
                    it,
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                )
            } != PackageManager.PERMISSION_GRANTED) {
            if (this.activity?.let {
                    ActivityCompat.shouldShowRequestPermissionRationale(
                        it,
                        android.Manifest.permission.ACCESS_FINE_LOCATION
                    )
                } == true)
                this.activity?.let {

                    ActivityCompat.requestPermissions(
                        it,
                        arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                        MY_PERMISSION_CODE
                    )
                }
            else
                this.activity?.let {
                    ActivityCompat.requestPermissions(
                        it,
                        arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                        MY_PERMISSION_CODE
                    )
                }
            return false

        }
        else return true
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode)
        {
            MY_PERMISSION_CODE->
            {
                if(grantResults.size>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
                {
                    if(this.activity?.let { ContextCompat.checkSelfPermission(it,android.Manifest.permission.ACCESS_FINE_LOCATION) } ==PackageManager.PERMISSION_GRANTED)
                        if(checkLocationPermission())
                        {
                            mMap!!.isMyLocationEnabled=true
                        }
                }
                else
                {
                    Toast.makeText(this.activity,"Permission Denied",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

            if (this.activity?.let {
                    ContextCompat.checkSelfPermission(
                        it,
                        android.Manifest.permission.ACCESS_FINE_LOCATION
                    )
                } != PackageManager.PERMISSION_GRANTED) {
                mMap.isMyLocationEnabled = true
            }
        mMap.isMyLocationEnabled=true
        mMap.uiSettings.isZoomControlsEnabled=true
        val latitude = 37.422160
        val longitude = -122.084270
        val zoomLevel = 15f
        val options = GoogleMapOptions()
        Log.i(TAG, "mission selected ${userMap.t}")
        val bounceBuilder = LatLngBounds.Builder()
        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        val lisbon = LatLng(38.71667, -9.13333)
        val homeLatLng = LatLng(latitude, longitude)
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(homeLatLng, zoomLevel))
        mMap.addMarker(MarkerOptions().position(homeLatLng))
    }
    override fun onResume() {
            super.onResume()
            startTimer(time_in_seconds)
        }
    override fun onStop() {
        super.onStop()
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
    }

        override fun onMarkerClick(p0: Marker?) = false


}






