package net.azarquiel.friendroom.model

import androidx.lifecycle.LiveData
import androidx.room.*

@Entity(tableName = "Families")
data class Family(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,

    @ColumnInfo(name = "name")
    var name: String = "",
)

@Dao
interface FamilyDAO {
    @Query("SELECT * FROM Families ORDER BY name ASC")
    fun getAll(): LiveData<List<Family>>

    @Query("SELECT * FROM Families WHERE id = :id")
    fun getById(id: Int): LiveData<List<Family>>

    @Insert
    fun add(friend: Family)

    @Query("DELETE FROM Families WHERE id = :id")
    fun remove(id: Int)

    @Update
    fun update(friend: Family)
}
