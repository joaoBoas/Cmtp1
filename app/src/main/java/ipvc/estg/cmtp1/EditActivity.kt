package ipvc.estg.cmtp1

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_edit.*

class EditActivity : AppCompatActivity(){


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_edit)


        var editIntent = intent
        var title = intent.getStringExtra("title")
        var description = intent.getStringExtra("description")
        var tag = intent.getStringExtra("tag")
        var id = intent.getStringExtra("id")


        editTitle.setText(title)
        editDescription.setText(description)

        //guardar nota
        btnEdit.setOnClickListener {

            title = editTitle.text.toString()
            description = editDescription.text.toString()

            editIntent.putExtra("title",title)
            editIntent.putExtra("description",description)

            editIntent.putExtra("id",id)

            setResult(Activity.RESULT_OK,editIntent)
            finish()
        }
    }
}