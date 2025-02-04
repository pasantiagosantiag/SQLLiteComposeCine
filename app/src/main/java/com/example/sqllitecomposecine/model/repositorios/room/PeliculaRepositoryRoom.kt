package com.example.sqllitecomposecine.model.repositorios.room

import com.example.sqllitecomposecine.model.entidades.Pelicula

import com.example.sqllitecomposecine.model.ormroom.RoomDB
import com.example.sqllitecomposecine.model.repositorios.APeliculaRepositorio
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking


class PeliculaRepositoryRoom(var db: RoomDB):  APeliculaRepositorio() {


    fun transformRoomToObject(peliculaRoom: com.example.sqllitecomposecine.model.entidades.room.PeliculaRoom): Pelicula {
       var item= Pelicula()
        item.id=peliculaRoom.id.toLong()
        item.nombre=peliculaRoom.nombre
        item.activo=peliculaRoom.activo
       // item.fecha=peliculaRoom.fe
        item.descripcion=peliculaRoom.descripcion
        item.duracion=peliculaRoom.duracion
        item.valoracion=peliculaRoom.valoracion
        item.uri=peliculaRoom.uri
       return item;
    }
    fun transformObjectToRoom(pelicula: Pelicula): com.example.sqllitecomposecine.model.entidades.room.PeliculaRoom {
        var item=com.example.sqllitecomposecine.model.entidades.room.PeliculaRoom()
        item.id=pelicula.id.toInt()
        item.activo=pelicula.activo
        item.nombre=pelicula.nombre
        item.uri=pelicula.uri
        item.descripcion=pelicula.descripcion
        item.duracion=pelicula.duracion
        item.valoracion=pelicula.valoracion
        return item;
    }
    override suspend fun getAll(): List<Pelicula> {
        var elements = ArrayList<Pelicula>();
        var consulta= runBlocking {  db.peliculaDao().getAll().first()}
        consulta.map {
            transformRoomToObject(it)
        }
        consulta.forEach({
            elements.add(transformRoomToObject(it))
        })
      return elements
    }

    override suspend fun remove(item: Pelicula) {
        this.removeById(item.id)

    }

    override suspend fun removeById(id: Long) {
        db.peliculaDao().deleteById(id.toInt());

    }

    override suspend fun getById(id: Long): Pelicula? {
       return db.peliculaDao().getById(id.toInt())?.let { transformRoomToObject(it) }
    }

    override suspend fun update(item: Pelicula) {
        this.updateById(item,item.id)
    }

    override suspend fun updateById(item: Pelicula, id: Long) {
        var tempo=transformObjectToRoom(item)
        db.peliculaDao().update(tempo)

    }

    override suspend fun add(item: Pelicula) {
        var tempo=transformObjectToRoom(item)
        var id=db.peliculaDao().insert(tempo)
        item.id=id.toLong()


    }

}
