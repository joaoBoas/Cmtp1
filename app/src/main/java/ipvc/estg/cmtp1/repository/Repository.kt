package ipvc.estg.cmtp1.repository

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import ipvc.estg.cmtp1.dao.NoteDao
import ipvc.estg.cmtp1.database.NoteRepository
import ipvc.estg.cmtp1.entities.Note

class Repository(app: Application) {


    var noteDao:NoteDao? = NoteRepository.getDatabase(app)?.noteDao()

    fun insert(note: Note) {
        InsertAsync(noteDao).execute(note)
    }

    fun delete(note:Note) {
        DeleteAsync(noteDao).execute(note)
    }

    fun update(note:Note) {
        UpdateAsync(noteDao).execute(note)
    }

    fun getAllNotes():LiveData<List<Note>> {
        return GetAllNotesAsync(noteDao).execute().get()
    }


    class InsertAsync(noteDao: NoteDao?):AsyncTask<Note,Void,Unit>() {
        var noteDao = noteDao
        override fun doInBackground(vararg p0: Note) {
            noteDao?.insert(p0[0])
        }
    }

    class DeleteAsync(noteDao: NoteDao?):AsyncTask<Note,Void,Unit>(){
        var noteDao = noteDao
        override fun doInBackground(vararg p0: Note) {
            noteDao?.delete(p0[0])
        }
    }

    class UpdateAsync(noteDao: NoteDao?):AsyncTask<Note,Void,Unit>(){
        var noteDao = noteDao
        override fun doInBackground(vararg p0: Note) {
            noteDao?.update(p0[0])
        }
    }

    class GetAllNotesAsync(noteDao: NoteDao?):AsyncTask<Unit,Void,LiveData<List<Note>>>(){
        var noteDao = noteDao
        override fun doInBackground(vararg p0: Unit?): LiveData<List<Note>>? {
            return noteDao?.getAllNotes()
        }
    }

}