package net.azarquiel.metro.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Line::class, Station::class], version = 1)
abstract class MetroDB: RoomDatabase() {
    abstract fun lineDAO(): LineDAO
    abstract fun stationDAO(): StationDAO

    companion object {
        @Volatile
        private var INSTANCE: MetroDB? = null

        fun getDB(context: Context): MetroDB {
            val tempInstance = INSTANCE
            if (tempInstance != null) return tempInstance

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MetroDB::class.java,
                    "MetroDB.db"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}