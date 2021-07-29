package ipvc.estg.cmtp1

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ipvc.estg.cmtp1.Adapters.LineAdapter
import ipvc.estg.cmtp1.entities.Note
import ipvc.estg.cmtp1.viewModel.NoteViewModel
import java.time.LocalDate


class NotesActivity: AppCompatActivity(), LineAdapter.SendInfo {

    private lateinit var noteViewModel: NoteViewModel
    private val addNoteActivityRequestCode = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.notes)

        //Change the title of Activity on Bar
        val actionBar = supportActionBar
        actionBar!!.title = getString(R.string.note)
        actionBar.setDisplayHomeAsUpEnabled(true)

        //RecyclerView
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        val adapter = LineAdapter(this, this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(DividerItemDecoration(this, 1))

        //ViewModel
        noteViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)
        noteViewModel.allNotes.observe(this, Observer { notes ->
            notes?.let { adapter.setNotes(it) }
        })

        //Add Note Button
        val addNote = findViewById<Button>(R.id.btn_add_note)
        addNote.setOnClickListener {
            val intent = Intent(this, AddNoteActivity::class.java)
            startActivityForResult(intent, addNoteActivityRequestCode)
        }
    }

    //Remove Note
    override fun catchingID(id: Int?) {
        noteViewModel.deleteById(id)
        Toast.makeText(this, R.string.toast_04, Toast.LENGTH_SHORT).show()
    }

    //Insert Note
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == addNoteActivityRequestCode && resultCode == Activity.RESULT_OK) {

            val titleVar = data?.getStringExtra("editText01")
            val descVar = data?.getStringExtra("editText02")
            val dateVar = LocalDate.now().toString()

            if (titleVar != null && descVar != null) {
                val note = Note(title = titleVar, description = descVar, date = dateVar)
                noteViewModel.insert(note)
            }
        } else {
            Toast.makeText(applicationContext, R.string.toast_05, Toast.LENGTH_LONG).show()
        }
    }
}