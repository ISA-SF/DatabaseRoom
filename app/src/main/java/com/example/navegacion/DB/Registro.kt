package com.example.navegacion.DB

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(tableName = Registro.Table_name)
data class Registro (
    @NotNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val regId: Int =0,

    @ColumnInfo(name = "name") val nombre: String,
    @ColumnInfo(name = "last_name") val apellido: String,
    @ColumnInfo(name = "gender") val genero: String
) {
    companion object{
        const val Table_name = "registro"
    }
}