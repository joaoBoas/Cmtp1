package ipvc.estg.cmtp1

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.R.layout
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_add.*

class AddActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        btnAdd.setOnClickListener {

            val  title = addTitle.text.toString()
            val description = addDescription.text.toString()

            val addIntent = Intent()
            addIntent.putExtra("Titlo",title)
            addIntent.putExtra("Descrição",description)

            setResult(Activity.RESULT_OK,addIntent)
            finish()
        }
    }
}