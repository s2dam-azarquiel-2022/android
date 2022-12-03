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

data class StationView(
    var name: String,
    var lines: List<Int>,
)

@Dao
interface StationDAO {
    @MapInfo(keyColumn = "nombre", valueColumn = "linea")
    @Query("""
      SELECT
        e.nombre,
        l.linea
      FROM estacion e
      LEFT JOIN (
        SELECT
          e.id,
	      e.linea,
	      e.nombre
        FROM estacion e
        WHERE e.linea != :line
      ) l ON l.nombre = e.nombre
      WHERE e.linea = :line
      ORDER BY e.id ASC
    """)
    fun getByLine(line: Int): LiveData<Map<String, List<Int>>>
}