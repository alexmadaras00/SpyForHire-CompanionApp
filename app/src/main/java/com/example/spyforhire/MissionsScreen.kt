package com.example.spyforhire

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.spyforhire.Model.MyPlaces
import com.example.spyforhire.Remote.Common
import com.example.spyforhire.Remote.IGoogleAPIService
import com.google.android.gms.location.*
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import kotlinx.android.synthetic.main.fragment_missions_screen.*
import retrofit2.Call
import retrofit2.Response
import java.lang.Thread.sleep


const val EXTRA_USER_MAP="EXTRA_USER_MAP"
const val TAG="Mission"

class MissionsScreen : Fragment(R.layout.fragment_missions_screen)  {
    public val REQUEST_LOCATION_PERMISSION = 1
    var location: Location= Location("myLocation")
    lateinit var service:IGoogleAPIService
    lateinit var l:ArrayList<CardView>
    lateinit var mMap: GoogleMap
    lateinit var userMap: CardView
    lateinit var locationRequest: LocationRequest
    lateinit var locationCallback: LocationCallback
    lateinit var fusedLocationClient: FusedLocationProviderClient
    lateinit var mLastLocation: Location
    lateinit var mMarker: Marker
    var latitude:Double?=0.toDouble()
    var longitude:Double?=0.toDouble()
    internal lateinit var currentPlaces: MyPlaces

    companion object {
        public val MY_PERMISSION_CODE: Int = 1000
    }
    var endTimer: Long? = 0
    val time: Long = 7L
    var sec = 3600L
    var timeStart = time * 1000L
    var START_MILLI_SECONDS = sec * 1000L
    var k: Int = 0
    var times:Int=0
    lateinit var countdown_timer: CountDownTimer
    var isRunning: Boolean = false
    var itList= ArrayList<CardView>()
    public var layoutManager: RecyclerView.LayoutManager? = null
    var complete: Boolean = false
    var count=0

    override fun onCreate(savedInstanceState: Bundle?) {


        Log.i(TAG, "Size:${itList.size}")

        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return super.onCreateView(inflater, container, savedInstanceState)
    }
    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {

        super.onViewCreated(itemView, savedInstanceState)


        if (this.activity?.let {
                ActivityCompat.checkSelfPermission(
                        it,
                        Manifest.permission.ACCESS_FINE_LOCATION
                )
            } != PackageManager.PERMISSION_GRANTED && this.activity?.let {
                ActivityCompat.checkSelfPermission(
                        it,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                )
            } != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
    ///    Log.i(TAG,"Latitude: ${fusedLocationClient.lastLocation.result.latitude}, Longitude: ${fusedLocationClient.lastLocation.result.longitude} \n")

            view?.findViewById<ImageView>(R.id.settings2)?.setOnClickListener {
                val intent = Intent(activity, Settings::class.java)
                intent.putExtra("time", timeStart)
                startActivity(intent)
            }
            view?.findViewById<ImageView>(R.id.trophy2)?.setOnClickListener {
                val intent = Intent(activity, Achievements::class.java)
                startActivity(intent)
            }
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
    @RequiresApi(Build.VERSION_CODES.O)
 /*   private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channel_name)
            val descriptionText = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(tag, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                    getSystemService(requireContext()) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }*/

    fun pauseTimer() {
        countdown_timer.cancel()
        isRunning = false
        updateTextUI()
    }

    fun updateTextUI() {
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
     fun resetTimer() {
        time_in_seconds = START_MILLI_SECONDS
        updateTextUI()
    }


    fun getLocationUpdates() {
        Toast.makeText(context, "Finding nearby places...", Toast.LENGTH_LONG).show()

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        locationRequest = LocationRequest.create()
        locationRequest.interval = 50000
        locationRequest.fastestInterval = 50000
        locationRequest.smallestDisplacement = 170f // 170 m = 0.1 mile
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY //set according to your app function
        println("MyLocation")

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult ?: return
                Log.i(TAG, "here")
                if (locationResult.locations.isNotEmpty()) {
                    // get latest location
                    location = locationResult.lastLocation
                    Log.i(TAG, "MyLocation: $location")
                    receiveLocation(location)
                    getNearbyLocation("museum", location)
                }


            }

        }
        startLocationUpdates()
    }

   private fun receiveLocation(receiveLocation: Location)
    {

     Log.i(TAG, "$receiveLocation")

    }
    private fun startLocationUpdates() {
        if (this.activity?.let {
                ActivityCompat.checkSelfPermission(
                        it,
                        Manifest.permission.ACCESS_FINE_LOCATION
                )
            } != PackageManager.PERMISSION_GRANTED && this.activity?.let {
                ActivityCompat.checkSelfPermission(
                        it,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                )
            } != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        fusedLocationClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                null
        )
    }

    // stop location updates
    private fun stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    // stop receiving location update when activity not visible/foreground

    var c=50
    fun getNearbyLocation(typePlace: String, location: Location)
    {
        k=0
        service=Common.googleApiService
        val url= getUrl(location.latitude, location.longitude, typePlace)
        service.getNearbyPlaces(url).enqueue(
                object : retrofit2.Callback<MyPlaces> {
                    override fun onResponse(call: Call<MyPlaces>, response: Response<MyPlaces>) {
                        currentPlaces = response.body()!!
                        if (response.isSuccessful) {
                            for (element in response.body()!!.results!!) {
                                k++
                                if (k == 5) break
                                val markerOptions = MarkerOptions()
                                val googlePlace = element
                                val lat = googlePlace.geometry!!.location!!.lat
                                val lng = googlePlace.geometry!!.location!!.lng
                                val placeName = googlePlace.name.toString()
                                Log.i(TAG, "Place: $placeName, $lat, $lng")
                                itList.add(
                                        CardView(
                                                "Go to $placeName, take a photo of it and then write when it was built and by who.",
                                                c,
                                                0,
                                                R.drawable.monetization_on_24px,
                                                0,
                                                R.id.bar1,
                                                false,
                                                lat, lng, placeName

                                        )
                                )
                                c += 50
                            }
                            itList[0].bar = 100
                            val recyclerView = view?.findViewById<RecyclerView>(R.id.recyclerView)
                            if (recyclerView != null) {
                                recyclerView.layoutManager = LinearLayoutManager(context)
                                recyclerView.adapter = Adapter(itList, object : Adapter.OnClickListener {
                                    override fun onItemClick(position: Int) {
                                        Log.i(TAG, "mission $position, latitude: ${itList[position].latitude}, longitude:${itList[position].longitude}")
                                        val intent = Intent(activity, MapsActivity::class.java)
                                        intent.putExtra("fLatitude", itList[position].latitude)
                                        intent.putExtra("fLongitude", itList[position].longitude)
                                        intent.putExtra("name", itList[position].name)

                                        println("Location: ${itList[position].name}, latitude: ${itList[position].latitude}, longitude:${itList[position].longitude} ")
                                        startActivity(intent)
                                    }
                                })
                            }
                        }
                    }

                    override fun onFailure(call: Call<MyPlaces>, t: Throwable) {
                        Toast.makeText(context, "" + t.message, Toast.LENGTH_SHORT).show()
                    }

                },
        )

    }

    fun getUrl(latitude: Double, longitude: Double, type: String): String {
        val googlePlaceUrl=StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json")
        googlePlaceUrl.append("?location=$latitude,$longitude")
        googlePlaceUrl.append("&radius=10000")
        googlePlaceUrl.append("&type=$type")
        googlePlaceUrl.append("&key=AIzaSyAgdbufQTuqqnTnAOVqcxM5vLS7TGaK_sE")
        Log.d("URL_DEBUG", googlePlaceUrl.toString())
        return googlePlaceUrl.toString()

    }
    var ok=false
    var x=0
    override fun onStart() {
        if(complete==false) {
            Toast.makeText(context, "Finding nearby places...", Toast.LENGTH_SHORT).show()
            getLocationUpdates()
            complete=true

        }
        else {
            itList
            var x=progressBar.progress
            if(ok==false) {
                for(el in itList)
                {
                    if(el.bar==100 && count<4 && x<100)
                    {
                        count+=1
                        x+=25
                    }
                }
                ok = true
            }
            progressBar.progress=x
            textView5.text=count.toString()
            MissionsScreen().activity?.supportFragmentManager?.beginTransaction()?.detach(this)?.attach(this)?.commit()
        }
        val recyclerView = view?.findViewById<RecyclerView>(R.id.recyclerView)
        if (recyclerView != null) {
            recyclerView.layoutManager = LinearLayoutManager(context)
            recyclerView.adapter = Adapter(itList, object : Adapter.OnClickListener {
                override fun onItemClick(position: Int) {
                    Log.i(TAG, "mission $position, latitude: ${itList[position].latitude}, longitude:${itList[position].longitude}")
                    val intent = Intent(activity, MapsActivity::class.java)
                    intent.putExtra("fLatitude", itList[position].latitude)
                    intent.putExtra("fLongitude", itList[position].longitude)

                    startActivity(intent)
                }
            })
        }

        startTimer(time_in_seconds)
        MissionsScreen().activity?.supportFragmentManager?.beginTransaction()?.detach(this)?.attach(this)?.commit()
        super.onStart()
    }
   override fun onStop()
    {
    ///    stopLocationUpdates()
        super.onStop()
    }

    override fun onPause() {


        super.onPause()
    }
    override fun onDestroy() {

        super.onDestroy()
    }
val cont=false
    override fun onResume() {

        itList
        view?.findViewById<TextView>(R.id.textView5)?.text
        val recyclerView = view?.findViewById<RecyclerView>(R.id.recyclerView)
        if (recyclerView != null) {
            recyclerView.layoutManager = LinearLayoutManager(context)
            recyclerView.adapter = Adapter(itList, object : Adapter.OnClickListener {
                override fun onItemClick(position: Int) {
                    Log.i(TAG, "mission $position, latitude: ${itList[position].latitude}, longitude:${itList[position].longitude}")
                    val intent = Intent(activity, MapsActivity::class.java)
                    intent.putExtra("fLatitude", itList[position].latitude)
                    intent.putExtra("fLongitude", itList[position].longitude)
                    intent.putExtra("name",itList[position].name)
                    startActivity(intent)
                }
            })
        }
        MissionsScreen().activity?.supportFragmentManager?.beginTransaction()?.detach(this)?.attach(this)?.commit()
        super.onResume()
    }


}






