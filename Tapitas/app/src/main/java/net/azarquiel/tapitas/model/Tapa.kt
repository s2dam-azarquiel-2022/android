package net.azarquiel.tapitas.model

import androidx.lifecycle.LiveData
import androidx.room.*

@Entity(
    tableName = "tapa",
    foreignKeys = [
        ForeignKey(
            entity = Stablishment::class,
            parentColumns = ["id"],
            childColumns = ["establecimiento"]
        )
    ]
)
data class Tapa (
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "establecimiento")
    val stablishment: Int,
    @ColumnInfo(name = "nombre")
    val name: String,
    @ColumnInfo(name = "descripcion")
    val description: String,
    @ColumnInfo(name = "url_imagen")
    val imageURL: String,
    @ColumnInfo(name = "fav")
    val favorite: Int,
)

data class TapaView (
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "nombre")
    val name: String,
    @ColumnInfo(name = "establecimiento")
    val stablishment: String,
    @ColumnInfo(name = "descripcion")
    val description: String,
    @ColumnInfo(name = "url_imagen")
    val imageURL: String,
    @ColumnInfo(name = "fav")
    val favorite: Int,
)

@Dao
interface TapaDAO {
    @Query("""
      SELECT
	    t.id,
	    t.nombre,
	    ( SELECT e.nombre
		    FROM establecimiento e
		    WHERE e.id = t.establecimiento
	    ) as establecimiento,
	    t.descripcion,
	    t.url_imagen,
	    t.fav
      FROM tapa t
    """)
    fun getAll(): LiveData<List<TapaView>>
}