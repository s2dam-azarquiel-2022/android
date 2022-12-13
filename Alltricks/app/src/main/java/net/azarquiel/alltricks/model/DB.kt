package net.azarquiel.alltricks.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Bike::class, Brand::class], version = 1)
abstract class AlltrickDB : RoomDatabase() {
    abstract fun bikeDAO(): BikeDAO
    abstract fun brandDAO(): BrandDAO

    companion object {
        @Volatile
        private var INSTANCE: AlltrickDB? = null

        fun getDB(context: Context): AlltrickDB {
            INSTANCE?.let { return it }

            synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    AlltrickDB::class.java,
                    "db.sqlite"
                ).build().let {
                    INSTANCE = it
                    return it
                }
            }
        }
    }
}