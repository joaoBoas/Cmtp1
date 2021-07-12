package ipvc.estg.cmtp1

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        //Variables for Shared Preferences
        val sharedPref: SharedPreferences = getSharedPreferences(
            getString(R.string.preference_file_key), Context.MODE_PRIVATE)
        val loginSharedPref = sharedPref.getBoolean(getString(R.string.loginSharedPref), false)
        val nameSharedPref = sharedPref.getString(getString(R.string.nameSharedPref), "")
        val idSharedPref = sharedPref.getInt(getString(R.string.idSharedPref), 0)

        //Disable title bar
        val actionBar = supportActionBar
        actionBar!!.hide()

        //Button to go for NotesActivity for check Personal Notes
        btn_notas.setOnClickListener {
            val intent = Intent (this, NotesActivity::class.java)
            startActivity(intent)
        }

        //Check if login is already have login and redirect to MainActivity
        if(loginSharedPref) {
            val intent = Intent (this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    fun loginSharedPref(view: View) {
        //Variables
        val nameText = findViewById<EditText>(R.id.username)
        val passText = findViewById<EditText>(R.id.password)

        val request = ServiceBuilder.buildService(EndPoints::class.java)
        val call = request.getUserByName(nameText.text.toString())
        val intent = Intent(this, MainActivity::class.java)

        //If Both fields empty
        if (nameText.text.toString().isEmpty() && passText.text.toString().isEmpty()) {
            Toast.makeText(applicationContext, getString(R.string.toast_06), Toast.LENGTH_SHORT).show()
        }
        //If Username field empty
        if (nameText.text.toString().isEmpty() && passText.text.toString().isNotEmpty()) {
            Toast.makeText(applicationContext, getString(R.string.toast_07), Toast.LENGTH_SHORT).show()
        }
        //If Password field empty
        if (nameText.text.toString().isNotEmpty() && passText.text.toString().isEmpty()) {
            Toast.makeText(applicationContext, getString(R.string.toast_08), Toast.LENGTH_SHORT).show()
        }
        //Fields filled
        else {
            call.enqueue(object : Callback<User> {
                override fun onResponse(call: Call<User>, response: Response<User>) {
                    if (response.isSuccessful) {
                        val u: User = response.body()!!

                        if (nameText.text.toString() == u.name && (passText.text.toString() == u.password)) {
                            startActivity(intent)
                            finish()

                            val sharedPref: SharedPreferences = getSharedPreferences(
                                getString(R.string.preference_file_key), Context.MODE_PRIVATE)
                            with(sharedPref.edit()){
                                putBoolean(getString(R.string.loginSharedPref), true)
                                putString(getString(R.string.nameSharedPref),"${nameText.text}")
                                putInt(getString(R.string.idSharedPref), u.id)
                                Log.d("Values", "$")
                                commit()
                            }
                            Toast.makeText(applicationContext, getString(R.string.welcome) + u.name, Toast.LENGTH_SHORT).show()

                        } else {
                            Toast.makeText(applicationContext, getString(R.string.toast_09), Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                override fun onFailure(call: Call<User>, t: Throwable) {
                    Toast.makeText(this@LoginActivity, "${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

}