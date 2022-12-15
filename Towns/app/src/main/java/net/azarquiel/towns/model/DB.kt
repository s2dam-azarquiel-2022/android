package net.azarquiel.towns.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        Community::class,
        Province::class,
        Town::class,
    ],
    version = 1
)
abstract class TownsDB : RoomDatabase() {
    abstract fun communityDAO(): CommunityDAO
    abstract fun provinceDAO(): ProvinceDAO
    abstract fun townDAO(): TownDAO

    companion object {
        @Volatile
        private var INSTANCE: TownsDB? = null

        fun getDB(context: Context): TownsDB {
            INSTANCE?.let { return it }

            synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    TownsDB::class.java,
                    "db.sqlite"
                ).build().let {
                    INSTANCE = it
                    return it
                }
            }
        }
    }
}