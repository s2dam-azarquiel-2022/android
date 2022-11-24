package net.azarquiel.friendroom.model

import android.graphics.Color
import androidx.lifecycle.LiveData
import androidx.room.*

@Entity(tableName = "Groups")
data class Group(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,

    @ColumnInfo(name = "name")
    var name: String = "",

    @ColumnInfo(name = "email")
    var email: String = "",

    @ColumnInfo(name = "color")
    var color: Int = Color.CYAN,
)

@Dao
interface GroupDAO {
    @Query("SELECT * FROM Groups ORDER BY name ASC")
    fun getAll(): LiveData<List<Group>>

    @Query("SELECT * FROM Groups WHERE id = :id")
    fun getById(id: Int): LiveData<List<Group>>

    @Insert
    fun add(group: Group)

    @Query("DELETE FROM Groups WHERE id = :id")
    fun remove(id: Int)

    @Update
    fun update(group: Group)
}
