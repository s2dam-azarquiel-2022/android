package net.azarquiel.alltricks.model

import androidx.lifecycle.LiveData
import androidx.room.*

@Entity(
    tableName = "bici",
    foreignKeys = [
        ForeignKey(entity = Brand::class, parentColumns = ["id"], childColumns = ["marca"])
    ]
)
data class Bike (
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "foto")
    val photo: String,

    @ColumnInfo(name = "marca")
    val brandID: Int,

    @ColumnInfo(name = "descripcion")
    val description: String,

    @ColumnInfo(name = "precio")
    val price: String,

    @ColumnInfo(name = "fav")
    val favorite: Int,
)

data class BikeListView (
    val id: Int,
    val photo: String,
    val description: String,
    val favorite: Int,
)

data class BikeDetailedView (
    val id: Int,
    val photo: String,
    val brand: String,
    val description: String,
    val price: String,
    val favorite: Int,
)

@Dao
interface BikeDAO {
    @Query("""
      SELECT
        b.id as id,
        b.foto as photo,
        b.descripcion as description,
        b.fav as favorite
      FROM bici b
      WHERE b.marca = :brandID
      ORDER BY id
    """)
    fun getByBrandId(brandID: Int): LiveData<List<BikeListView>>

    @Query("""
      SELECT
        b.id as id,
        b.foto as photo,
	    ( SELECT m.nombre
		  FROM marca m
		  WHERE m.id = b.marca
	    ) as brand,
	    b.descripcion as description,
	    b.precio as price,
	    b.fav as favorite
      FROM bici b
      WHERE b.id = :id
      ORDER BY id
    """)
    fun getById(id: Int): LiveData<List<BikeDetailedView>>

    @Query("""
      UPDATE bici
      SET fav = (
        SELECT
          CASE WHEN fav = 1
          THEN 0
          ELSE 1
          END AS toggledFav
        FROM bici
        WHERE id = :id
      ) WHERE id = :id
    """)
    fun toggleFavorite(id: Int)
}
