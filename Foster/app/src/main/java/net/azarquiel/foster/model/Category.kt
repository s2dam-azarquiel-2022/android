package net.azarquiel.foster.model

import androidx.lifecycle.LiveData
import androidx.room.*

@Entity(tableName = "categoria")
data class Category (
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int?,

    @ColumnInfo(name = "nombre")
    val name: String?,

    @ColumnInfo(name = "descripcion")
    val description: String?,

    @ColumnInfo(name = "guarnicion")
    val garrison: String?,
)

@Dao
interface CategoryDAO {
    @Query("""
      SELECT
	    c.id as id,
	    c.nombre as name,
	    c.descripcion as description,
	    c.guarnicion as garrison
      FROM categoria c
      ORDER BY id
    """)
    fun getAll(): LiveData<List<Category>>
}