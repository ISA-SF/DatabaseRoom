package com.example.navegacion.DB

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface RegistroDao {

    @Insert
    suspend fun insert(registro: Registro)

    @Query("SELECT * FROM registro")
    fun getRegistros() : MutableList<Registro>

    /*@Query("SELECT * FROM registro WHERE id = :id")
    suspend fun getId(id: Int) : Registro*/

    @Query("DELETE FROM registro WHERE name =:name")
    suspend fun deleteN(name:String)

}