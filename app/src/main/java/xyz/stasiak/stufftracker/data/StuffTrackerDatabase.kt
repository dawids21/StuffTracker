package xyz.stasiak.stufftracker.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [Item::class, Category::class],
    version = 3,
    exportSchema = false
)
abstract class StuffTrackerDatabase : RoomDatabase() {

    abstract fun itemDao(): ItemDao
    abstract fun categoryDao(): CategoryDao

    companion object {
        @Volatile
        private var Instance: StuffTrackerDatabase? = null

        fun getDatabase(context: Context): StuffTrackerDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context,
                    StuffTrackerDatabase::class.java,
                    "stuff_tracker_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}
