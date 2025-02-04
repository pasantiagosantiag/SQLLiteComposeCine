package com.example.sqllitecomposecine.model.repositorios.room

import com.example.sqllitecomposecine.model.entidades.Pelicula
import com.example.sqllitecomposecine.model.entidades.Sala
import com.example.sqllitecomposecine.model.entidades.Sesion
import com.example.sqllitecomposecine.model.entidades.room.SesionRoom
import com.example.sqllitecomposecine.model.entidades.room.SesionWithPelicualAndSala
import com.example.sqllitecomposecine.model.ormroom.RoomDB
import com.example.sqllitecomposecine.model.repositorios.ASalaRepositorio
import com.example.sqllitecomposecine.model.repositorios.ASesionRepositorio
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import java.time.Instant
import java.time.ZoneId
import java.time.ZoneOffset


class SesionRepositoryRoom(var db: RoomDB) : ASesionRepositorio() {
    fun transformRoomToObject(sesionRoom: SesionWithPelicualAndSala): Sesion {
        var item = Sesion()
        var sala = Sala()
        var pelicula = Pelicula()
        item.id = sesionRoom.sesion.id.toLong()
        item.fecha_y_hora = Instant.ofEpochSecond(sesionRoom.sesion.fecha_y_hora)
            .atZone(ZoneId.systemDefault()) // Usa la zona horaria local
            .toLocalDateTime();
        sala.id = sesionRoom.sala.id.toLong()
        sala.columnas = sesionRoom.sala.columnas
        sala.filas = sesionRoom.sala.filas
        sala.estado = sesionRoom.sala.estado
        sala.nombre = sesionRoom.sala.nombre
        sala.descripcion = sesionRoom.sala.descripcion
        item.sala = sala


        pelicula.id = sesionRoom.pelicula.id.toLong()
        pelicula.activo = sesionRoom.pelicula.activo
        pelicula.nombre = sesionRoom.pelicula.nombre

        pelicula.descripcion = sesionRoom.pelicula.descripcion
        pelicula.duracion = sesionRoom.pelicula.duracion
        pelicula.valoracion = sesionRoom.pelicula.valoracion
        pelicula.uri = sesionRoom.pelicula.uri
        item.pelicula = pelicula
        return item;
    }

    fun transformObjectToRoom(sesion: Sesion): SesionRoom {
        var item = SesionRoom()
        item.id = sesion.id.toInt()
        item.pelicula = sesion.pelicula.id.toInt()
        item.sala = sesion.sala.id.toInt()
        item.fecha_y_hora = sesion.fecha_y_hora.toEpochSecond(ZoneOffset.UTC)
        return item;
    }

    override suspend fun getAll(): List<Sesion> {
        var elements = ArrayList<Sesion>();
        var consulta = runBlocking { db.sesionDao().getAll().first() }
        consulta.map {
            transformRoomToObject(it)
        }
        consulta.forEach({
            elements.add(transformRoomToObject(it))
        })
        return elements
    }

    override suspend fun remove(item: Sesion) {
        this.removeById(item.id)

    }

    override suspend fun removeById(id: Long) {
        db.sesionDao().deleteById(id.toInt());

    }

    override suspend fun getById(id: Long): Sesion? {
        return db.sesionDao().getById(id.toInt())?.let { transformRoomToObject(it) }
    }

    override suspend fun update(item: Sesion) {
        this.updateById(item, item.id)
    }

    override suspend fun updateById(item: Sesion, id: Long) {
        var tempo = transformObjectToRoom(item)
        db.sesionDao().update(tempo)

    }

    override suspend fun add(item: Sesion) {
        var tempo = transformObjectToRoom(item)
        val id = db.sesionDao().insert(tempo)
        item.id = id.toLong()

    }

}
