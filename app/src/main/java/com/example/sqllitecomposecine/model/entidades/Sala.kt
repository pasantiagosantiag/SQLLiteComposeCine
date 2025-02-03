package com.example.sqllitecomposecine.model.entidades

import android.graphics.Bitmap
import androidx.compose.ui.graphics.vector.ImageVector
import com.caverock.androidsvg.SVG

data class Sala(
    var id:Long,
    var nombre:String,
    var descripcion:String,
    var estado:Boolean,
    var filas:Int,
    var columnas:Int) {
    constructor() : this(0,"","",false,1,1)
}