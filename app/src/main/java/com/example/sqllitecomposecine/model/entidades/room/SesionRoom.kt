package com.example.sqllitecomposecine.model.entidades.room

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.example.sqllitecomposecine.model.entidades.Pelicula

@Entity(tableName = "SesionRoom")
data class SesionRoom(
    @PrimaryKey(autoGenerate = true)
    var id:Int=0,

    //@Embedded val pelicula: PeliculaRoom,
    /* @Relation(
         parentColumn = "id",
         entityColumn = "pelicula"
     )
     val pelicula: PeliculaRoom,*/
    /* @Relation(
         parentColumn = "id",
         entityColumn = "sala"
     )
     val sala: SalaRoom
*/
    ) {
   // constructor() : this(0,"","",false,1,1)
}