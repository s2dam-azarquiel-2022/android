package net.azarquiel.metro.model

import androidx.lifecycle.LiveData
import androidx.room.*
import java.io.Serializable

@Entity(tableName = "linea")
data class Line (
    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: Int = 0,

    @ColumnInfo(name = "nombre")
    var name: String = "",

    @ColumnInfo(name = "color")
    var color: String = "",
)

data class LineView(
    var id: Int,
    var name: String,
    var color: String,
    var startEnd: String,
) : Serializable

@Dao
interface LineDAO {
    @Query("""
      SELECT
        linea.id,
        linea.nombre name,
        linea.color,
        lineas.startEnd
      FROM
        ( SELECT
            l.linea,
            e1.nombre || ' - ' || e2.nombre startEnd
          FROM
            ( SELECT
                e.linea linea,
          	    MIN(e.id) mini,
          	    MAX(e.id) maxi
                FROM estacion e
                GROUP BY e.linea
            ) l,
            estacion e1,
            estacion e2
          WHERE l.mini = e1.id
            AND l.maxi = e2.id
        ) lineas,
        linea linea
      WHERE lineas.linea = linea.id
    """)
    fun getAll(): LiveData<List<LineView>>
}