package ipvc.estg.cmtp1.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import ipvc.estg.cmtp1.entities.Note

@Dao


interface NoteDao {

    @Query("SELECT * FROM note_table ORDER BY date DESC")
    fun getAllNotes(): LiveData<List<Note>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(note: Note)

    @Query("DELETE FROM note_table")
    suspend fun deleteAll()

    @Query("DELETE FROM note_table WHERE id = :id")
    fun deleteById(id: Int?)

    @Query("UPDATE note_table SET title = :title, description = :description, date = :date WHERE id == :id")
    suspend fun updateNote(title: String, description: String, date: String, id: Int?)
}