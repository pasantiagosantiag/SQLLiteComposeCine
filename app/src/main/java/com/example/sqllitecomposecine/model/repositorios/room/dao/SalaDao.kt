package com.example.sqllitecomposecine.model.repositorios.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.sqllitecomposecine.model.entidades.room.SalaRoom

@Dao
interface SalaDao {
    @Insert
    suspend fun insert(item: SalaRoom)

    @Query("SELECT * FROM SalaRoom")
    fun getAll(): kotlinx.coroutines.flow.Flow<List<SalaRoom>>

    @Query("SELECT * FROM SalaRoom WHERE SalaRoom.id = :ide")
    suspend fun getById(ide: Int): SalaRoom?

    @Update
    suspend fun update(item: SalaRoom)

    @Delete
    suspend fun delete(item: SalaRoom)

    @Query("DELETE FROM SalaRoom WHERE SalaRoom.id = :id")
    suspend fun deleteById(id: Int)
}