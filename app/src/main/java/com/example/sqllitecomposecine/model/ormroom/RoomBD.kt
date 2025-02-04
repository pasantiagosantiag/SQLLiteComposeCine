package com.example.sqllitecomposecine.model.ormroom

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.sqllitecomposecine.model.entidades.Pelicula
import com.example.sqllitecomposecine.model.entidades.room.PeliculaRoom
import com.example.sqllitecomposecine.model.entidades.room.SalaRoom
import com.example.sqllitecomposecine.model.entidades.room.SesionRoom
import com.example.sqllitecomposecine.model.repositorios.room.dao.PeliculaDao
import com.example.sqllitecomposecine.model.repositorios.room.dao.SalaDao
import com.example.sqllitecomposecine.model.repositorios.room.dao.SesionDao


@Database(entities = [SalaRoom::class, PeliculaRoom::class, SesionRoom::class], version = 1, exportSchema = false)
abstract class RoomDB : RoomDatabase() {
    abstract fun salaDao(): SalaDao

    abstract fun peliculaDao(): PeliculaDao
    abstract  fun sesionDao():SesionDao
    companion object {
        @Volatile
        private var INSTANCE: RoomDB? = null

        fun getDatabase(context: Context): RoomDB {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RoomDB::class.java,
                    "cineroom.db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}