package net.azarquiel.tapitas.model

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Entity
import androidx.room.PrimaryKey

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

@Dao
interface StablishmentDAO {
}
