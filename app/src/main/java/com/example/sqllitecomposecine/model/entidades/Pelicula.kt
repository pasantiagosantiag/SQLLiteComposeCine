package com.example.sqllitecomposecine.model.entidades

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.time.LocalDate

data class Pelicula (
    var id:Long, var nombre:String, val fecha: LocalDate,
    var descripcion:String,
    var uri:String,
    var valoracion:Int,
    var activo:Boolean,
    var duracion:Int) {
    constructor() : this(0,"", LocalDate.now(),"",
        "", //Bitmap.createBitmap(300, 200, Bitmap.Config.ARGB_8888)
        -1,true,-1)
}