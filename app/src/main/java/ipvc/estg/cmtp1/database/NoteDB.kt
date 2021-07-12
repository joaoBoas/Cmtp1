package ipvc.estg.cmtp1.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import ipvc.estg.cmtp1.dao.NoteDao
import ipvc.estg.cmtp1.entities.Note
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [Note::class], version = 7, exportSchema = false)
public abstract class NoteDB: RoomDatabase() {

    abstract fun noteDao(): NoteDao

    private class NoteDatabaseCallback(private val scope: CoroutineScope) : RoomDatabase.Callback() {

        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let { database ->
                scope.launch {
                    val noteDao = database.noteDao()

                    //Delete all content here
                    //noteDao.deleteAll()

                    //Add sample words
                    /*var note = Note(1, "Note 1", "Sou uma description", "2021-04-06")
                    noteDao.insert(note)
                    note = Note(2, "Note 2", "Sou uma description 2", "2021-04-06")
                    noteDao.insert(note)*/

                }
            }
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: NoteDB? = null

        fun getDatabase(context: Context, scope: CoroutineScope): NoteDB {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NoteDB::class.java,
                    "note_database"
                )
                    //Destruction Strategy
                    //.fallbackToDestructiveMigration()
                    .addCallback(NoteDatabaseCallback(scope))
                    .build()

                INSTANCE = instance
                return instance
            }
        }
    }
}