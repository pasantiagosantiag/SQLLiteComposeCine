package com.example.sqllitecomposecine.model.repositorios.memoria

import android.content.res.Resources
import android.graphics.BitmapFactory
import androidx.lifecycle.viewModelScope
import com.caverock.androidsvg.SVG
import com.example.sqllitecomposecine.R
import com.example.sqllitecomposecine.model.entidades.Pelicula
import com.example.sqllitecomposecine.model.entidades.Sala
import com.example.sqllitecomposecine.model.entidades.Sesion
import com.example.sqllitecomposecine.model.repositorios.APeliculaRepositorio
import com.example.sqllitecomposecine.model.repositorios.ASalaRepositorio
import com.example.sqllitecomposecine.model.repositorios.ASesionRepositorio
import com.example.sqllitecomposecine.model.repositorios.IRepositorio

import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.Month


class SesionRepositioEnMemoria(
    private val salaRepositio: ASalaRepositorio,
    private val peliculaRepositio: APeliculaRepositorio
) : ASesionRepositorio() {
    private var _items = mutableListOf<Sesion>()

    init {
        var img = BitmapFactory.decodeResource(Resources.getSystem(), R.drawable.anillos)

        val svg =
            SVG.getFromString(" <svg width='300' height='130' xmlns='http://www.w3.org/2000/svg'> <rect width='200' height='100' x='10' y='10' rx='20' ry='20' fill='blue' /> </svg> ")
        runBlocking {
            launch {
                val p1=peliculaRepositio.getById(1L)
                val s1=salaRepositio.getById(1L)
                _items.add(
                    Sesion(
                        1,
                        p1!!,
                        s1!!,
                        LocalDateTime.of(2024, Month.FEBRUARY,3,17,0,0)
                    )
                )
                _items.add(
                    Sesion(
                        2,
                        p1!!,
                        s1!!,
                        LocalDateTime.of(2024, Month.FEBRUARY,3,20,15,0)
                    )
                )
                _items.add(
                    Sesion(
                        3,
                        peliculaRepositio.getById(1L)!!,
                        salaRepositio.getById(1L)!!,
                        LocalDateTime.of(2024, Month.FEBRUARY,4,17,0,0)
                    )
                )
                _items.add(
                    Sesion(
                        4,
                        peliculaRepositio.getById(1L)!!,
                        salaRepositio.getById(1L)!!,
                        LocalDateTime.of(2024, Month.FEBRUARY,3,21,0,0)
                    )
                )



                _items.add(
                    Sesion(
                        5,
                        peliculaRepositio.getById(2L)!!,
                        salaRepositio.getById(2L)!!,
                        LocalDateTime.of(2024, Month.FEBRUARY,3,17,0,0)
                    )
                )
                _items.add(
                    Sesion(
                        6,
                        peliculaRepositio.getById(2L)!!,
                        salaRepositio.getById(2L)!!,
                        LocalDateTime.of(2024, Month.FEBRUARY,3,20,15,0)
                    )
                )
                _items.add(
                    Sesion(
                        7,
                        peliculaRepositio.getById(2L)!!,
                        salaRepositio.getById(2L)!!,
                        LocalDateTime.of(2024, Month.FEBRUARY,4,17,0,0)
                    )
                )
                _items.add(
                    Sesion(
                        8,
                        peliculaRepositio.getById(2L)!!,
                        salaRepositio.getById(2L)!!,
                        LocalDateTime.of(2024, Month.FEBRUARY,3,21,0,0)
                    )
                )
            }
        }
    }

    fun getSesionesBySala(sala: Sala): List<Sesion> {
        return _items.toList().filter {
            it.sala.id == sala.id
        }
    }

    fun getSesionesByPelicula(pelicula: Pelicula): List<Sesion> {
        return _items.toList().filter {
            it.pelicula.id == pelicula.id
        }
    }

    override suspend fun getAll(): List<Sesion> {
        return _items.toList()

    }

    override suspend fun remove(item: Sesion) {
        _items.remove(item)
    }

    override suspend fun removeById(id: Long) {
        _items.removeIf({ it.id == id })
    }

    override suspend fun getById(id: Long): Sesion? {
        return _items.first { it.id == id }
    }

    override suspend fun update(item: Sesion) {
        _items.replaceAll {
            if (it.id == item.id)
                item
            else
                it
        }
    }

    override suspend fun add(item: Sesion) {
        this._items.add(item)
    }

    override suspend fun updateById(item: Sesion, id: Long) {

    }
}