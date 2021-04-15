package ipvc.estg.cmtp1.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ipvc.estg.cmtp1.dao.NotasDao
import ipvc.estg.cmtp1.entities.Notas

@Database(entities = arrayOf(Notas::class),version = 1)
abstract class NotasDatabase : RoomDatabase(){
    abstract fun noteDao():NotasDao

    companion object{
        var instance:NotasDatabase? = null
        fun getDatabase(context: Context):NotasDatabase?{
            if (instance == null) {
                instance = Room.databaseBuilder(context.applicationContext,NotasDatabase::class.java,
                    "notas_database").build()
            }

            return instance
        }
    }
}