package ipvc.estg.cmtp1

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.add_notes.*

class AddNoteActivity : AppCompatActivity(){

    private lateinit var editText01: EditText
    private lateinit var editText02: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_notes)

        editText01 = findViewById(R.id.editText01)
        editText02 = findViewById(R.id.editText02)

        val button = findViewById<Button>(R.id.button01)
        button.setOnClickListener {
            val replyIntent = Intent()
            if (TextUtils.isEmpty(editText01.text) || TextUtils.isEmpty(editText02.text)) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            }
            else {
                replyIntent.putExtra("editText01", editText01.text.toString())
                replyIntent.putExtra("editText02", editText02.text.toString())

                setResult(Activity.RESULT_OK, replyIntent)
                Toast.makeText(applicationContext, R.string.toast_01, Toast.LENGTH_SHORT).show()
            }
            finish()
        }
    }
}