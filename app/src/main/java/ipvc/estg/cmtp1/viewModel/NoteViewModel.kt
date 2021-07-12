package ipvc.estg.cmtp1.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import ipvc.estg.cmtp1.database.NoteDB
import ipvc.estg.cmtp1.database.NoteRepository
import ipvc.estg.cmtp1.entities.Note
import ipvc.estg.cmtp1.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteViewModel(application: Application) : AndroidViewModel(application)  {

    private val repository: NoteRepository
    val allNotes: LiveData<List<Note>>

    init {
        val notesDao = NoteDB.getDatabase(application, viewModelScope).noteDao()
        repository = NoteRepository(notesDao)
        allNotes = repository.allNotes
    }

    fun insert(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(note)
    }

    fun edit(title: String, description: String, date: String, id: Int?) = viewModelScope.launch {
        repository.updateNote(title, description, date, id)
    }

    fun deleteById(id: Int?) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteByID(id)
    }
}