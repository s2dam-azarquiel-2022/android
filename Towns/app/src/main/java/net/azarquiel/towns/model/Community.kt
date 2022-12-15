package net.azarquiel.towns.model

import androidx.lifecycle.LiveData
import androidx.room.*

@Entity(tableName = "comunidad")
data class Community (
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "nombre")
    val name: String,
)

@Dao
interface CommunityDAO {
    @Query("""
      SELECT
	    c.id,
	    c.nombre
      FROM comunidad c
      ORDER BY c.id
    """)
    fun getAll(): LiveData<List<Community>>
}