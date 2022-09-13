package com.example.navegacion.DB

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Registro::class], version = 1)
abstract class RegistroDatabase : RoomDatabase() {
    abstract fun registroDao(): RegistroDao
}