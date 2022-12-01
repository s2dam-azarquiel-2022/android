package net.azarquiel.metro.model

import androidx.lifecycle.LiveData
import androidx.room.*

@Entity(tableName = "estacion")
data class Station(
    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: Int = 0,

    @ColumnInfo(name = "nombre")
    var name: String = "",

    @ColumnInfo(name = "linea")
    var lineID: Int = 0
)

@Dao
interface StationDAO {
    @Query("SELECT * FROM estacion WHERE linea = :line")
    fun getById(line: Int): LiveData<List<Station>>
}