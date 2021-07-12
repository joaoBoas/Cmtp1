package ipvc.estg.cmtp1.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ipvc.estg.cmtp1.dao.NoteDao
import ipvc.estg.cmtp1.entities.Note

class NoteRepository(private val noteDao: NoteDao) {
    val allNotes: LiveData<List<Note>> = noteDao.getAllNotes()

    suspend fun insert(note: Note) {
        noteDao.insert(note)
    }

    suspend fun updateNote(title: String, description: String, date: String, id: Int?) {
        noteDao.updateNote(title, description, date, id)
    }

    fun deleteByID(id: Int?) {
        noteDao.deleteById(id)
    }
}