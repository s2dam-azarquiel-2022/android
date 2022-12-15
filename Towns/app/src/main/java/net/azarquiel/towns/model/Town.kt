package net.azarquiel.towns.model

import androidx.lifecycle.LiveData
import androidx.room.*

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

@Dao
interface TownDAO {
    @Query("""
      SELECT
	    p.id,
	    p.nombre,
	    p.imagen,
	    p.provincia,
	    p.link,
	    p.fav
      FROM pueblo p
      WHERE p.provincia IN (
	    SELECT pr.id
	    FROM provincia pr
	    WHERE pr.comunidad = :communityID
      )
    """)
    fun getByCommunityID(communityID: Int): LiveData<List<Town>>
}