package com.example.sqllitecomposecine.model.entidades.room

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.example.sqllitecomposecine.model.entidades.Pelicula
import java.time.LocalDateTime

@Entity(tableName = "SesionRoom")
data class SesionRoom(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var pelicula: Int,
    var sala: Int,
    var fecha_y_hora: Long

) {
    constructor() : this(0, 0, 0,0)
}

data class SesionWithPelicualAndSala(
    @Embedded val sesion: SesionRoom, @Relation(
        parentColumn = "pelicula", entityColumn = "id"
    )
    val pelicula: PeliculaRoom,
    @Relation(
        parentColumn = "sala", entityColumn = "id"
    )
    val sala: SalaRoom
)
