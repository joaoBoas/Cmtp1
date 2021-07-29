package ipvc.estg.cmtp1

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        //Variables Shared Preferences
        val sharedPref: SharedPreferences = getSharedPreferences(
            getString(R.string.preference_file_key), Context.MODE_PRIVATE)
        val idUser= sharedPref.getInt(getString(R.string.idSharedPref), 0)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        val request = ServiceBuilder.buildService(EndPoints::class.java)
        val call = request.getReports()
        var position: LatLng

        call.enqueue(object : Callback<List<Report>> {
            override fun onResponse(call: Call<List<Report>>, response: Response<List<Report>>) {
                if (response.isSuccessful) {
                    reports = response.body()!!

                    for(report in reports) {
                        if(report.users_id == idUser) {
                            position = LatLng(report.lat.toString().toDouble(), report.lng.toString().toDouble())
                            mMap.addMarker(MarkerOptions().position(position).title(report.name)
                                .snippet(report.description)
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)))
                            //mMap.setInfoWindowAdapter()
                        }
                        else {
                            position = LatLng(report.lat.toString().toDouble(), report.lng.toString().toDouble())
                            mMap.addMarker(MarkerOptions().position(position).title(report.name)
                                .snippet(report.description)
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)))

                        }
                    }
                }
            }
            override fun onFailure(call: Call<List<Report>>, t: Throwable) {
                Toast.makeText(this@MapsActivity, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    //Function to call the menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    //Items from Menu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            //R.id.profile -> {
            //    val intent = Intent (this, ProfileActivity::class.java)
            //    startActivity(intent)
            //}
            R.id.notes -> {
                val intent = Intent (this, NotesActivity::class.java)
                startActivity(intent)
            }
            R.id.help -> {
                val intent = Intent (this, HelpActivity::class.java)
                startActivity(intent)
            }
            R.id.logout -> {
                val sharedPref: SharedPreferences = getSharedPreferences(
                    getString(R.string.preference_file_key), Context.MODE_PRIVATE)
                with(sharedPref.edit()){
                    putBoolean(getString(R.string.loginSharedPref), false)
                    putString(getString(R.string.nameSharedPref), "")
                    putInt(getString(R.string.idSharedPref), 0)
                    commit()
                }

                val intent = Intent (this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
        return true
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val center = LatLng(41.373215, -8.487476)
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(center, 12F))
    }
}