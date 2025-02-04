package com.example.sqllitecomposecine.model.repositorios.memoria

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.example.sqllitecomposecine.R
import com.example.sqllitecomposecine.model.entidades.Pelicula
import com.example.sqllitecomposecine.model.repositorios.APeliculaRepositorio
import com.example.sqllitecomposecine.model.repositorios.IRepositorio
import java.time.LocalDate

class PeliculaRepositioEnMemoria: APeliculaRepositorio() {//PeliculaRepositorio() {
    private var _peliculas= mutableListOf<Pelicula>()
    init {
        //var img=BitmapFactory.decodeResource(Resources.getSystem(), R.drawable.anillos)
        _peliculas.add(Pelicula (1,"El señor de los anillos, la comunidad del anillo", LocalDate.now(),"En la Tierra Media, el Señor Oscuro Sauron ordenó a los Elfos que forjaran los Grandes Anillos de Poder. Tres para los reyes Elfos, siete para los Señores Enanos, y nueve para los Hombres Mortales. Pero Saurón también forjó, en secreto, el Anillo Único, que tiene el poder de esclavizar toda la Tierra Media.","",
            //Bitmap.createBitmap(300, 200, Bitmap.Config.ARGB_8888)
            5,true,120))
        _peliculas.add(Pelicula (2,"Spiderman", LocalDate.now(),"Mordido por una araña genéticamente modificada, un estudiante de secundaria tímido y torpe obtiene increíbles capacidades como arácnido. Pronto comprenderá que su cometido es utilizarlas para luchar contra el mal y defender a sus vecinos..",
            "",
                   // Bitmap.createBitmap(300, 200, Bitmap.Config.ARGB_8888) ,
    5,true,120))

    }
    override suspend fun getAll(): List<Pelicula> {
        return _peliculas.toList()

    }

    override suspend fun remove(item: Pelicula) {
        _peliculas.remove(item)
    }

    override suspend fun removeById(id: Long) {
        _peliculas.removeIf({ it.id==id})
    }

    override suspend fun getById(id: Long): Pelicula? {
        return _peliculas.first { it.id==id }
    }

    override suspend fun update(item: Pelicula) {
        _peliculas.replaceAll {
            if(it.id==item.id)
                item
            else
                it
        }
    }

    override suspend fun add(item: Pelicula) {
       _peliculas.add(item)
    }
    override suspend fun updateById(item: Pelicula, id: Long) {

    }
}