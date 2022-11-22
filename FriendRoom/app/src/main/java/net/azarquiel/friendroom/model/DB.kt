package net.azarquiel.friendroom.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Friend::class, Family::class], version = 1)
abstract class FriendsDB: RoomDatabase() {
    abstract fun friendDAO(): FriendDAO
    abstract fun familyDAO(): FamilyDAO

    companion object {
        @Volatile
        private var INSTANCE: FriendsDB? = null

        fun getDB(context: Context): FriendsDB {
            val tempInstance = INSTANCE
            if (tempInstance != null) return tempInstance

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FriendsDB::class.java,
                    "FriendsDS"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}