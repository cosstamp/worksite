package ro.lemacons.lemaworksite.data

import android.arch.persistence.room.*
import java.util.*


@Entity
    (
    tableName = "santiere"
)
data class Santiere (
    @PrimaryKey
    @ColumnInfo(name = "id_santier") var id_santier: Long,
    @ColumnInfo(name = "nume_santier") var nume_santier: String?,
    @ColumnInfo(name = "cod_santier") var cod_santier: String?,
    @ColumnInfo(name = "data_fin_santier") var data_fin_santier: String?,
    @ColumnInfo(name = "buget") var buget: Int?
)