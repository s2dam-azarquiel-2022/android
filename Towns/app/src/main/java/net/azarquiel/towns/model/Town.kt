package net.azarquiel.towns.model

import androidx.lifecycle.LiveData
import androidx.room.*
import java.io.Serializable

@Entity(
    tableName = "pueblo",
    foreignKeys = [
        ForeignKey(
            entity = Province::class,
            parentColumns = ["id"],
            childColumns = ["provincia"]
        )
    ]
)
data class Town (
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "nombre")
    val name: String,

    @ColumnInfo(name = "imagen")
    val image: String,

    @ColumnInfo(name = "provincia")
    val provinceID: Int,

    @ColumnInfo(name = "link")
    val link: String,

    @ColumnInfo(name = "fav")
    val favorite: Int,
)

data class TownView (
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "nombre")
    val name: String,
    @ColumnInfo(name = "imagen")
    val image: String,
    @ColumnInfo(name = "provincia")
    val province: String,
    @ColumnInfo(name = "link")
    val link: String,
    @ColumnInfo(name = "fav")
    val favorite: Int,
) : Serializable

@Dao
interface TownDAO {
    @Query("""
      SELECT
	    p.id,
	    p.nombre,
	    p.imagen,
	    ( SELECT pr.nombre
          FROM provincia pr
          WHERE pr.id = p.provincia
        ) as provincia,
	    p.link,
	    p.fav
      FROM pueblo p
      WHERE p.provincia IN (
	    SELECT pr.id
	    FROM provincia pr
	    WHERE pr.comunidad = :communityID
      )
    """)
    fun getByCommunityID(communityID: Int): LiveData<List<TownView>>

    @Query("""
      SELECT
	    p.id,
	    p.nombre,
	    p.imagen,
	    ( SELECT pr.nombre
          FROM provincia pr
          WHERE pr.id = p.provincia
        ) as provincia,
	    p.link,
	    p.fav
      FROM pueblo p
      WHERE p.id = :id
    """)
    fun getById(id: Int): LiveData<List<TownView>>

    @Query("""
      UPDATE pueblo
      SET fav = (
        SELECT
          CASE WHEN fav = 1
          THEN 0
          ELSE 1
          END AS toggledFav
        FROM pueblo
        WHERE id = :id
      )
      WHERE id = :id
    """)
    fun toggleFav(id: Int)
}