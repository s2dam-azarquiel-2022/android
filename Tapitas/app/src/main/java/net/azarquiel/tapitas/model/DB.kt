package net.azarquiel.tapitas.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        Tapa::class,
        Stablishment::class,
    ],
    version = 1
)
abstract class TapasDB : RoomDatabase() {
    abstract fun tapaDAO(): TapaDAO
    abstract fun stablishmentDAO(): StablishmentDAO

    companion object {
        @Volatile
        private var INSTANCE: TapasDB? = null

        fun getDB(context: Context): TapasDB {
            INSTANCE?.let { return it }

            synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    TapasDB::class.java,
                    "db.db"
                ).build().let {
                    INSTANCE = it
                    return it
                }
            }
        }
    }
}