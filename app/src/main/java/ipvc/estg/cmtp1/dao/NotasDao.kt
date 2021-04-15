package ipvc.estg.cmtp1.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import ipvc.estg.cmtp1.entities.Notas

@Dao


interface NotasDao {
    @Insert
    fun insert(note: Notas)

    @Update
    fun update(note: Notas)

    @Delete
    fun delete(note: Notas)

    @Query("SELECT * FROM notas_table ORDER BY id ASC")
    fun getAllNotes():LiveData<List<Notas>>
}