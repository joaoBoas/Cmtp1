package ipvc.estg.cmtp1

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import ipvc.estg.cmtp1.api.EndPoints
import ipvc.estg.cmtp1.api.Report
import ipvc.estg.cmtp1.api.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var reports: List<Report>
    private lateinit var lastLocation: Location
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback
    private lateinit var locationRequest: LocationRequest
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        //Variables Shared Preferences
        val sharedPref: SharedPreferences = getSharedPreferences(
            getString(R.string.preference_file_key), Context.MODE_PRIVATE
        )
        val idUser = sharedPref.getInt(getString(R.string.idSharedPref), 0)
        val latSharedPref = sharedPref.getFloat(getString(R.string.latSharedPref), 0.0f)
        val lngSharedPref = sharedPref.getFloat(getString(R.string.lngSharedPref), 0.0f)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        val request = ServiceBuilder.buildService(EndPoints::class.java)
        val call = request.getReports()
        var position: LatLng

        call.enqueue(object : Callback<List<Report>> {
            override fun onResponse(call: Call<List<Report>>, response: Response<List<Report>>) {
                if (response.isSuccessful) {
                    reports = response.body()!!

                    for (report in reports) {
                        position = LatLng(
                            report.lat.toString().toDouble(),
                            report.lng.toString().toDouble()
                        )
                        if (report.users_id == idUser) {
                            // position = LatLng(report.lat.toString().toDouble(), report.lng.toString().toDouble())
                            mMap.addMarker(
                                MarkerOptions().position(position).title(report.name)
                                    .snippet(report.description)
                                    .icon(
                                        BitmapDescriptorFactory.defaultMarker(
                                            BitmapDescriptorFactory.HUE_CYAN
                                        )
                                    )
                            )
                            //mMap.setInfoWindowAdapter()
                        } else {
                            // position = LatLng(report.lat.toString().toDouble(), report.lng.toString().toDouble())
                            mMap.addMarker(
                                MarkerOptions().position(position).title(report.name)
                                    .snippet(report.description)
                                    .icon(
                                        BitmapDescriptorFactory.defaultMarker(
                                            BitmapDescriptorFactory.HUE_RED
                                        )
                                    )
                            )

                        }
                    }
                }
            }

            override fun onFailure(call: Call<List<Report>>, t: Throwable) {
                Toast.makeText(this@MapsActivity, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(p0: LocationResult) {
                super.onLocationResult(p0)
                lastLocation = p0.lastLocation
                val coordinates = LatLng(lastLocation.latitude, lastLocation.longitude)

                //Save coordinates in shared preferences
                val sP: SharedPreferences = getSharedPreferences(
                    getString(R.string.preference_file_key), Context.MODE_PRIVATE
                )
                with(sP.edit()) {
                    putFloat(getString(R.string.latSharedPref), lastLocation.latitude.toFloat())
                    putFloat(getString(R.string.lngSharedPref), lastLocation.longitude.toFloat())
                    Log.d("Coordinates", "$")
                    commit()
                }

                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(coordinates, 15f))
                Log.d("*** Location ***", "new location received")
            }
        }

        //request location
        createLocationRequest()
    }

    //Function to call the menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    //Items from Menu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.addReport -> {
                val intent = Intent(this, AddReportActivity::class.java)
                startActivity(intent)
            }
            R.id.notes -> {
                val intent = Intent(this, NotesActivity::class.java)
                startActivity(intent)
            }
            R.id.help -> {
                val intent = Intent(this, HelpActivity::class.java)
                startActivity(intent)
            }
            R.id.logout -> {
                val sharedPref: SharedPreferences = getSharedPreferences(
                    getString(R.string.preference_file_key), Context.MODE_PRIVATE
                )
                with(sharedPref.edit()) {
                    putBoolean(getString(R.string.loginSharedPref), false)
                    putString(getString(R.string.nameSharedPref), "")
                    putInt(getString(R.string.idSharedPref), 0)
                    commit()
                }

                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
        return true
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        //val center = LatLng(41.373215, -8.487476)
        //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(center, 12F))
    }

    //added to implement periodic location updates
    private fun startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
            return
        }
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null)
    }

    private fun createLocationRequest() {
        locationRequest = LocationRequest()
        //interval specifies the time that the app is receiving updates
        locationRequest.interval = 10000
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }

    override fun onPause() {
        super.onPause()
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
        Log.d("****Location****", "onPause - endLocationUpdates")
    }

    public override fun onResume() {
        super.onResume()
        startLocationUpdates()
        Log.d("****Location****", "onResume - startLocationUpdates")
    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
        private const val REQUEST_CHECK_SETTINGS = 2
    }
}
