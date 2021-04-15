package ipvc.estg.cmtp1.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notas_table")
data class Notas (
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val title: String,
    val description: String){

}
