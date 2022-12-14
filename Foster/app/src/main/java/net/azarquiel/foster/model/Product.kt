package net.azarquiel.foster.model

import androidx.lifecycle.LiveData
import androidx.room.*

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

data class ProductListView (
    val title: String?,
    val image: String?,
)

data class ProductDetailedView (
    val title: String?,
    val body: String?,
    val image: String?,
    val summary: String?,
)

@Dao
interface ProductDAO {
    @Query("""
      SELECT
	    p.id as id,
	    p.titulo as title,
	    p.imagen as image
      FROM producto p
      WHERE p.categoriaid = :categoryID
    """)
    fun getByCategoryID(categoryID: Int): LiveData<List<ProductListView>>

    @Query("""
      SELECT
	    p.id as id,
	    p.titulo as title,
	    p.body as body,
	    p.imagen as image,
	    p.sumario as summary
      FROM producto p
      WHERE p.id = :id
    """)
    fun getByID(id: Int): LiveData<List<ProductDetailedView>>
}