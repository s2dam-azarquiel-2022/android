package net.azarquiel.foster.model

import androidx.lifecycle.LiveData
import androidx.room.*
import java.io.Serializable

@Entity(
    tableName = "producto",
    foreignKeys = [
        ForeignKey(entity = Category::class, parentColumns = ["id"], childColumns = ["categoriaid"])
    ]
)
data class Product (
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int?,

    @ColumnInfo(name = "titulo")
    val title: String?,

    @ColumnInfo(name = "body")
    val body: String?,

    @ColumnInfo(name = "categoriaid")
    val categoryID: Int?,

    @ColumnInfo(name = "imagen")
    val image: String?,

    @ColumnInfo(name = "fondo")
    val background: String?,

    @ColumnInfo(name = "sumario")
    val summary: String?,
)

data class ProductDetailedView (
    val id: Int?,
    val title: String?,
    val body: String?,
    val image: String?,
    val summary: String?,
    var favorite: Boolean,
) : Serializable

@Dao
interface ProductDAO {
    @Query("""
      SELECT
	    p.id as id,
	    p.titulo as title,
	    p.body as body,
	    p.imagen as image,
	    p.sumario as summary,
        'false' as favorite
      FROM producto p
      WHERE p.categoriaid = :categoryID
    """)
    fun getByCategoryID(categoryID: Int): LiveData<List<ProductDetailedView>>
}