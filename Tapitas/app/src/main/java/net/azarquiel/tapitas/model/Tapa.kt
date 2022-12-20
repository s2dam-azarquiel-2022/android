package net.azarquiel.tapitas.model

import androidx.lifecycle.LiveData
import androidx.room.*
import java.io.Serializable

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
) : Serializable


data class TapaDetailedView (
    @ColumnInfo(name = "nombre")
    val name: String,
    @ColumnInfo(name = "descripcion")
    val description: String,
    @ColumnInfo(name = "url_imagen")
    val imageURL: String,
    @ColumnInfo(name = "fav")
    val favorite: Int,
    @ColumnInfo(name = "eID")
    val stablishmentID: Int,
    @ColumnInfo(name = "eNombre")
    val stablishmentName: String,
    @ColumnInfo(name = "direccion")
    val direction: String,
    @ColumnInfo(name = "url_imagen_exterior")
    val stablishmentImageURL: String,
) : Serializable

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

    @Query("""
      SELECT
	    t.nombre,
	    t.descripcion,
        t.url_imagen,
        t.fav,
	    st.id as eID,
	    st.nombre as eNombre,
	    st.direccion,
	    st.url_imagen_exterior
      FROM tapa t
      LEFT JOIN (
	    SELECT
		    e.id,
		    e.nombre,
		    e.direccion,
		    e.url_imagen_exterior
	    FROM establecimiento e
      ) st ON t.establecimiento = st.id
      WHERE t.id = :id
    """)
    fun getById(id: Int): LiveData<List<TapaDetailedView>>

    @Query("""
      UPDATE tapa
      SET fav = (
        SELECT
          CASE WHEN fav = 1
          THEN 0
          ELSE 1
          END AS toggledFav
        FROM tapa
        WHERE id = :id
      )
      WHERE id = :id
    """)
    fun toggleFav(id: Int)
}