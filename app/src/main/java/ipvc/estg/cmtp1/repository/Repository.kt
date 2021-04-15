package ipvc.estg.cmtp1.repository

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import ipvc.estg.cmtp1.dao.NotasDao
import ipvc.estg.cmtp1.database.NotasDatabase
import ipvc.estg.cmtp1.entities.Notas

class Repository(app: Application) {

    var noteDao: NotasDao? = NotasDatabase.getDatabase(app)?.noteDao()

    fun insert(note: Notas) {
        InsertAsync(noteDao).execute(note)
    }

    fun delete(note:Notas) {
        DeleteAsync(noteDao).execute(note)
    }

    fun update(note:Notas) {
        UpdateAsync(noteDao).execute(note)
    }

    fun getAllNotes(): LiveData<List<Notas>> {
        return GetAllNotesAsync(noteDao).execute().get()
    }


    class InsertAsync(noteDao: NotasDao?): AsyncTask<Notas, Void, Unit>() {
        var noteDao = noteDao
        override fun doInBackground(vararg p0: Notas) {
            noteDao?.insert(p0[0])
        }
    }

    class DeleteAsync(noteDao: NotasDao?): AsyncTask<Notas, Void, Unit>(){
        var noteDao = noteDao
        override fun doInBackground(vararg p0: Notas) {
            noteDao?.delete(p0[0])
        }
    }

    class UpdateAsync(noteDao: NotasDao?): AsyncTask<Notas, Void, Unit>(){
        var noteDao = noteDao
        override fun doInBackground(vararg p0: Notas) {
            noteDao?.update(p0[0])
        }
    }

    class GetAllNotesAsync(noteDao: NotasDao?): AsyncTask<Unit, Void, LiveData<List<Notas>>>(){
        var noteDao = noteDao
        override fun doInBackground(vararg p0: Unit?): LiveData<List<Notas>>? {
            return noteDao?.getAllNotes()
        }
    }


}