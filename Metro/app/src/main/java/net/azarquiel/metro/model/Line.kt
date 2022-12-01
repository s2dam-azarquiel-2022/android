package net.azarquiel.metro.model

import androidx.lifecycle.LiveData
import androidx.room.*

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
    var id: Int = 0,
    var name: String = "",
    var color: String = "",
    var startEnd: String = "",
)

@Dao
interface LineDAO {
    @Query(
        """
      SELECT
        linea.id,
        linea.nombre name,
        linea.color,
        lineas.startEnd
      FROM
        ( SELECT
            lineas.linea,
            lineas.mini,
            lineas.maxi,
            'Estacion inicio: ' || e1.nombre || ' Estacion final:' || e2.nombre startEnd,
            linea.color,
            linea.nombre
          FROM
            ( SELECT
                linea,
          	    MIN(id) mini,
          	    MAX(id) maxi
                FROM estacion e
                GROUP BY linea
            ) lineas,
            estacion e1,
            estacion e2,
            linea linea
          WHERE lineas.mini = e1.id
            AND lineas.maxi = e2.id
        ) lineas,
        linea linea
      WHERE lineas.linea = linea.id
    """
    )
    fun getAll(): LiveData<List<LineView>>
}