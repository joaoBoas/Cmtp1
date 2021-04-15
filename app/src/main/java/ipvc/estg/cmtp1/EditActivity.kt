package ipvc.estg.cmtp1

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_edit.*

class EditActivity : AppCompatActivity(){
    var color:String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_edit)

        //getting values from intent
        var editIntent = intent
        var title = intent.getStringExtra("title")
        var description = intent.getStringExtra("description")
        var tag = intent.getStringExtra("tag")
        var id = intent.getStringExtra("id")

        //fill the edit text with note values
        editTitle.setText(title)
        editDescription.setText(description)

        //Save note
        btnEdit.setOnClickListener {

            //getting values from edittext
            title = editTitle.text.toString()
            description = editDescription.text.toString()



            //sending values via intent
            editIntent.putExtra("title",title)
            editIntent.putExtra("description",description)

            editIntent.putExtra("id",id)


            setResult(Activity.RESULT_OK,editIntent)
            finish()
        }
    }
}