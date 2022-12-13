package net.azarquiel.alltricks.model

import androidx.lifecycle.LiveData
import androidx.room.*
import java.io.Serializable

@Entity(tableName = "marca")
data class Brand (
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "nombre")
    val name: String,
) : Serializable

@Dao
interface BrandDAO {
    @Query(
        """
      SELECT
        m.id,
        m.nombre
      FROM marca m
      ORDER BY id
    """
    )
    fun getAll(): LiveData<List<Brand>>
}
