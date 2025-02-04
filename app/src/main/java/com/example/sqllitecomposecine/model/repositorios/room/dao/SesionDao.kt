package com.example.sqllitecomposecine.model.repositorios.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.sqllitecomposecine.model.entidades.room.SesionRoom
import com.example.sqllitecomposecine.model.entidades.room.SesionWithPelicualAndSala


@Dao
interface SesionDao {
    @Insert
    suspend fun insert(item: SesionRoom):Long

    @Query("SELECT * FROM SesionRoom")
    fun getAll(): kotlinx.coroutines.flow.Flow<List<SesionWithPelicualAndSala>>

    @Query("SELECT * FROM SesionRoom WHERE SesionRoom.id = :ide")
    suspend fun getById(ide: Int): SesionWithPelicualAndSala?

    @Update
    suspend fun update(item: SesionRoom)

    @Delete
    suspend fun delete(item: SesionRoom)

    @Query("DELETE FROM SesionRoom WHERE SesionRoom.id = :id")
    suspend fun deleteById(id: Int)
}