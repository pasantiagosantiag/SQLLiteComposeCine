package com.example.sqllitecomposecine.model.repositorios.memoria

import com.caverock.androidsvg.SVG
import com.example.sqllitecomposecine.model.entidades.Pelicula
import com.example.sqllitecomposecine.model.entidades.Sala
import com.example.sqllitecomposecine.model.repositorios.ASalaRepositorio
import com.example.sqllitecomposecine.model.repositorios.IRepositorio

class SalaRepositioEnMemoria: ASalaRepositorio() {
    private var _items= mutableListOf<Sala>()
    init {
        val svg = SVG.getFromString(" <svg width='300' height='130' xmlns='http://www.w3.org/2000/svg'> <rect width='200' height='100' x='10' y='10' rx='20' ry='20' fill='blue' /> </svg> ")

        _items.add(Sala(1,"Sala 1","Sala grande",true,30,40))
        _items.add(Sala(2,"Sala 2","Sala mediana",true,10,20))
        _items.add(Sala(3,"Sala 3","Sala pequeña",true,10,10))

        // _items.add(Sala (1,"El señor de los anillos, la comunidad del anillo", LocalDate.now(),"En la Tierra Media, el Señor Oscuro Sauron ordenó a los Elfos que forjaran los Grandes Anillos de Poder. Tres para los reyes Elfos, siete para los Señores Enanos, y nueve para los Hombres Mortales. Pero Saurón también forjó, en secreto, el Anillo Único, que tiene el poder de esclavizar toda la Tierra Media.",img ,5,true,120))
       // _items.add(Sala (2,"Spiderman", LocalDate.now(),"Mordido por una araña genéticamente modificada, un estudiante de secundaria tímido y torpe obtiene increíbles capacidades como arácnido. Pronto comprenderá que su cometido es utilizarlas para luchar contra el mal y defender a sus vecinos..",img ,5,true,120))

    }
    override suspend fun getAll(): List<Sala> {
        return _items.toList()

    }

    override suspend fun remove(item: Sala) {
        _items.remove(item)
    }

    override suspend fun removeById(id: Long) {
        _items.removeIf({ it.id==id})
    }

    override suspend fun getById(id: Long): Sala? {
        return _items.first { it.id==id }
    }

    override suspend fun update(item: Sala) {
        _items.replaceAll {
            if(it.id==item.id)
                item
            else
                it
        }
    }
    override suspend fun add(item: Sala) {
        var maximo=_items.maxByOrNull { it.id }
        if(maximo==null)
            item.id=1L
        else
            item.id=maximo.id+1
        _items.add(item)
    }
    override suspend fun updateById(item: Sala, id: Long) {

    }
}