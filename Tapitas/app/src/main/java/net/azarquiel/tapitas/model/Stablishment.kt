package net.azarquiel.tapitas.model

import androidx.lifecycle.LiveData
import androidx.room.*

@Entity(tableName = "establecimiento")
data class Stablishment(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "nombre")
    val name: String,
    @ColumnInfo(name = "direccion")
    val direction: String,
    @ColumnInfo(name = "telefono")
    val telephone: String,
    @ColumnInfo(name = "url_imagen_exterior")
    val imageURL: String,
    @ColumnInfo(name = "latitud")
    val latitude: Float,
    @ColumnInfo(name = "longitud")
    val longitude: Float,
)

data class StablishmentView(
    @ColumnInfo(name = "nombre")
    val name: String,
    @ColumnInfo(name = "direccion")
    val direction: String,
    @ColumnInfo(name = "url_imagen_exterior")
    val imageURL: String,
)

@Dao
interface StablishmentDAO {
    @Query("""
      SELECT
	    e.nombre,
	    e.direccion,
	    e.url_imagen_exterior
      FROM establecimiento e
      WHERE e.id = :id
    """)
    fun getById(id: Int): LiveData<List<StablishmentView>>

    @Query("""
      SELECT
	    t.nombre
      FROM tapa t
      WHERE t.establecimiento = :id
    """)
    fun getRecipesById(id: Int): LiveData<List<String>>
}
