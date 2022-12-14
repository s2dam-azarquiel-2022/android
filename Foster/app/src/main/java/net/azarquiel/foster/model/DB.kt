package net.azarquiel.foster.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Category::class, Product::class], version = 1)
abstract class FosterDB : RoomDatabase() {
    abstract fun categoryDAO(): CategoryDAO
    abstract fun productDAO(): ProductDAO

    companion object {
        @Volatile
        private var INSTANCE: FosterDB? = null

        fun getDB(context: Context): FosterDB {
            INSTANCE?.let { return it }

            synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    FosterDB::class.java,
                    "db.sqlite"
                ).build().let {
                    INSTANCE = it
                    return it
                }
            }
        }
    }
}