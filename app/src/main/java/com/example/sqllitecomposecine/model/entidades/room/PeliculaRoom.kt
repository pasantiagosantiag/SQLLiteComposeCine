package com.example.sqllitecomposecine.model.entidades.room

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.time.LocalDate
/*@Entity(tableName = "PeliculaRoom")
data class PeliculaRoom (
    @PrimaryKey(autoGenerate = true)
    var id:Int=0,
    var nombre:String,

    var descripcion:String,

    var valoracion:Int,
    var activo:Boolean,
    var duracion:Int) {
    constructor() : this(0,"", "",

        -1,true,-1)
}*/

@Entity(tableName = "PeliculaRoom")
data class PeliculaRoom(
    @PrimaryKey(autoGenerate = true)
    var id:Int=0,
    var nombre:String,

    var descripcion:String,

    var valoracion:Int,
    var activo:Boolean,
    var duracion:Int,
    var uri:String,
) {
    constructor() : this(0,"","",0,false,0,"")
}