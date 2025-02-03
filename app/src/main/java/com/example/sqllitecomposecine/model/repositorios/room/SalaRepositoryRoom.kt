package com.example.sqllitecomposecine.model.repositorios.room

import com.example.sqllitecomposecine.model.entidades.Sala
import com.example.sqllitecomposecine.model.ormroom.RoomDB
import com.example.sqllitecomposecine.model.repositorios.ASalaRepositorio
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking


class SalaRepositoryRoom(var db: RoomDB):  ASalaRepositorio() {


    fun transformRoomToObject(salaRoom: com.example.sqllitecomposecine.model.entidades.room.SalaRoom): Sala {
       var item=Sala()
        item.id=salaRoom.id.toLong()
        item.columnas=salaRoom.columnas
        item.filas=salaRoom.filas
        item.estado=salaRoom.estado
        item.nombre=salaRoom.nombre
        item.descripcion=salaRoom.descripcion
       return item;
    }
    fun transformObjectToRoom(sala: Sala): com.example.sqllitecomposecine.model.entidades.room.SalaRoom {
        var item=com.example.sqllitecomposecine.model.entidades.room.SalaRoom()
        item.id=sala.id.toInt()
        item.columnas=sala.columnas
        item.filas=sala.filas
        item.estado=sala.estado
        item.nombre=sala.nombre
        item.descripcion=sala.descripcion
        return item;
    }
    override suspend fun getAll(): List<Sala> {
        var elements = ArrayList<Sala>();
        var consulta= runBlocking {  db.salaDao().getAll().first()}
        consulta.map {
            transformRoomToObject(it)
        }
        consulta.forEach({
            elements.add(transformRoomToObject(it))
        })
      return elements
    }

    override suspend fun remove(item: Sala) {
        this.removeById(item.id)

    }

    override suspend fun removeById(id: Long) {
        db.salaDao().deleteById(id.toInt());

    }

    override suspend fun getById(id: Long): Sala? {
       return db.salaDao().getById(id.toInt())?.let { transformRoomToObject(it) }
    }

    override suspend fun update(item: Sala) {
        this.updateById(item,item.id)
    }

    override suspend fun updateById(item: Sala, id: Long) {
        var tempo=transformObjectToRoom(item)
        db.salaDao().update(tempo)

    }

    override suspend fun add(item: Sala) {
        var tempo=transformObjectToRoom(item)
        db.salaDao().insert(tempo)


    }

}
