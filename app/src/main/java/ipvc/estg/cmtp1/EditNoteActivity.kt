package ipvc.estg.cmtp1

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import ipvc.estg.cmtp1.Adapters.DESC
import ipvc.estg.cmtp1.Adapters.ID
import ipvc.estg.cmtp1.Adapters.TITLE
import ipvc.estg.cmtp1.viewModel.NoteViewModel
import kotlinx.android.synthetic.main.edit_notes.*
import java.time.LocalDate

class EditNoteActivity : AppCompatActivity(){


    private lateinit var noteViewModel: NoteViewModel
    private lateinit var title: EditText
    private lateinit var description: EditText

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_notes)

        //Save variables of Note
        val titleValue: String? = intent.extras?.getString(TITLE)
        val descValue: String? = intent.extras?.getString(DESC)
        val idValue: Int? = intent.extras?.getInt(ID)

        noteViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)

        title = findViewById(R.id.title)
        description = findViewById(R.id.description)
        title.setText(titleValue)
        description.setText(descValue)

        val buttonEdit = findViewById<Button>(R.id.btn_edit)
        buttonEdit.setOnClickListener {
            val replyIntent = Intent()
            if (TextUtils.isEmpty(title.text) || TextUtils.isEmpty(description.text)) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
                Toast.makeText(applicationContext, R.string.toast_02, Toast.LENGTH_SHORT).show()
            }
            else {
                if (idValue != null) {
                    noteViewModel.edit(title.text.toString(), description.text.toString(), LocalDate.now().toString(), idValue)
                    Toast.makeText(applicationContext, R.string.toast_03, Toast.LENGTH_SHORT).show()
                }
            }
            finish()
        }
    }
}