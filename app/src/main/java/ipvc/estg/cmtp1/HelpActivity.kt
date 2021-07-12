package ipvc.estg.cmtp1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class HelpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.help)

        //Change the title of Activity on Bar
        val actionBar = supportActionBar
        actionBar!!.title = "Help"
        actionBar.setDisplayHomeAsUpEnabled(true)

    }
}