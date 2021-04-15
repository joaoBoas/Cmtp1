package ipvc.estg.cmtp1.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import ipvc.estg.cmtp1.entities.Notas
import ipvc.estg.cmtp1.repository.Repository

class ViewModel(app: Application): AndroidViewModel(app) {
    var repository: Repository = Repository(app)

    fun insert(note: Notas) {
        repository.insert(note)
    }

    fun delete(note: Notas) {
        repository.delete(note)
    }

    fun update(note: Notas) {
        repository.update(note)
    }

    fun getAllNotes(): LiveData<List<Notas>> {
        return repository.getAllNotes()
    }
}