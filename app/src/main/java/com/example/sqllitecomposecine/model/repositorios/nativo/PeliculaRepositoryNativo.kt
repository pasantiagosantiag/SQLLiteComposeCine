package com.example.sqllitecomposecine.model.repositorios.nativo

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.example.sqllitecomposecine.model.entidades.Pelicula
import com.example.sqllitecomposecine.model.repositorios.APeliculaRepositorio



class PeliculaRepositoryNativo():  APeliculaRepositorio() {

    private var TABLENAME:String="PELICULA"
private lateinit var db:SQLiteDatabase
    public fun setDb(db:SQLiteDatabase){
        this.db=db;
    }
    fun transform(c: Cursor): Pelicula {
        var item=Pelicula()
        item.id=c.getLong(c.getColumnIndexOrThrow("id"));
        item.activo=c.getInt(c.getColumnIndexOrThrow("activo"))>0;
        item.nombre=c.getString(c.getColumnIndexOrThrow("nombre"));
        item.descripcion=c.getString(c.getColumnIndexOrThrow("descripcion"));
       item.uri=c.getString(c.getColumnIndexOrThrow("uri"));
        // item.fecha=c.getInt(c.getColumnIndexOrThrow("filas"))
        item.duracion=c.getInt(c.getColumnIndexOrThrow("duracion"))
        item.valoracion=c.getInt(c.getColumnIndexOrThrow("valoracion"))
        return item;
    }
    override suspend fun getAll(): List<Pelicula> {

        val projection = arrayOf("id", "nombre","fecha","uri","valoracion","duracion", "activo", "descripcion")
        var elements = ArrayList<Pelicula>();
        val cursor = db.query(
            TABLENAME,   // The table to query
            projection,             // The array of columns to return (pass null to get all)
            null,              // The columns for the WHERE clause
            null,          // The values for the WHERE clause
            null,                   // don't group the rows
            null,                   // don't filter by row groups
            null               // The sort order
        )
        while(cursor.moveToNext()){
            elements.add(transform(cursor))

        }

        cursor.close()
        return elements
    }

    override suspend fun remove(item: Pelicula) {
        this.removeById(item.id)

    }

    override suspend fun removeById(id: Long) {
        val selection = "id = ?"
        val selectionArgs = arrayOf(id.toString())
        db.delete(TABLENAME, selection, selectionArgs);
    }

    override suspend fun getById(id: Long): Pelicula? {
        TODO("Not yet implemented")
    }

    override suspend fun update(item: Pelicula) {
        this.updateById(item,item.id)
    }

    override suspend fun updateById(item: Pelicula, id: Long) {

        val values = ContentValues().apply {
            put("nombre",item.nombre)
            put("activo",item.activo)
            put("uri",item.uri)
            put("descripcion",item.descripcion)
            put("duracion",item.duracion)
            put("valoracion",item.valoracion)

        }


        val selection = "id = ?"
        val selectionArgs = arrayOf(item.id.toString())
        db.update(
            TABLENAME,
            values,
            selection,
            selectionArgs)

    }

    override suspend fun add(item: Pelicula) {

        val values = ContentValues().apply {
            put("nombre",item.nombre)
            put("activo",item.activo)
            put("uri",item.uri)
            put("descripcion",item.descripcion)
            put("duracion",item.duracion)
            put("valoracion",item.valoracion)
        }
        var id=db.insert(TABLENAME,null,values);
        item.id=id.toLong();

    }
  
}
