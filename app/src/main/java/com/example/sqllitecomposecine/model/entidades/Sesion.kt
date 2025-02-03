package com.example.sqllitecomposecine.model.entidades

import android.graphics.Bitmap
import java.time.LocalDate
import java.time.LocalDateTime

data class Sesion (
    var id:Long,
    var pelicula:Pelicula,
    var sala:Sala,
    var fecha_y_hora: LocalDateTime) {
    //se crea las filas y columnas sin ocupar
    constructor() : this(0L,
        Pelicula(),
        Sala(),
        LocalDateTime.now())

    private var _plazas=Array<Array<Boolean>>(sala.filas){
        Array(sala.columnas){
            false
        }
    }
    //private var _image=sala.mapa
    public fun ocupar(f:Int,c:Int){
        _plazas.get(f)[c]=true
    }
    public fun ocupada(f:Int,c:Int):Boolean{
        return _plazas.get(f)[c];
    }
    public fun anular(f:Int,c:Int){
        _plazas.get(f)[c]=false
    }
}