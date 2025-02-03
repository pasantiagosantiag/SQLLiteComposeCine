package com.example.sqllitecomposecine.model.repositorios.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.sqllitecomposecine.model.entidades.room.PeliculaRoom

@Dao
interface PeliculaDao {
    @Insert
    suspend fun insert(item: PeliculaRoom)

    @Query("SELECT * FROM PeliculaRoom")
    fun getAll(): kotlinx.coroutines.flow.Flow<List<PeliculaRoom>>

    @Query("SELECT * FROM PeliculaRoom WHERE PeliculaRoom.id = :ide")
    suspend fun getById(ide: Int): PeliculaRoom?

    @Update
    suspend fun update(item: PeliculaRoom)

    @Delete
    suspend fun delete(item: PeliculaRoom)

    @Query("DELETE FROM PeliculaRoom WHERE PeliculaRoom.id = :id")
    suspend fun deleteById(id: Int)
}