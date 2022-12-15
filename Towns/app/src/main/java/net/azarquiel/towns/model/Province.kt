package net.azarquiel.towns.model

import androidx.room.*

@Entity(
    tableName = "provincia",
    foreignKeys = [
        ForeignKey(
            entity = Community::class,
            parentColumns = ["id"],
            childColumns = ["comunidad"]
        )
    ]
)
data class Province (
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "nombre")
    val name: String,

    @ColumnInfo(name = "comunidad")
    val communityID: Int,
)

@Dao
interface ProvinceDAO {

}
