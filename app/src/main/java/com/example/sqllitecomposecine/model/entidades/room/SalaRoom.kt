package com.example.sqllitecomposecine.model.entidades.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "SalaRoom")
data class SalaRoom(
    @PrimaryKey(autoGenerate = true)
    var id:Int=0,
    var nombre:String,
    var descripcion:String,
    var estado:Boolean,
    var filas:Int,
    var columnas:Int) {
    constructor() : this(0,"","",false,1,1)
}