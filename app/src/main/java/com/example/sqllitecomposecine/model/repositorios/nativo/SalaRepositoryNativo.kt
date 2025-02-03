package com.example.sqllitecomposecine.model.repositorios.nativo

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.example.sqllitecomposecine.model.entidades.Sala
import com.example.sqllitecomposecine.model.repositorios.ASalaRepositorio



class SalaRepositoryNativo():  ASalaRepositorio() {

    private var TABLENAME:String="SALA"
private  var db:SQLiteDatabase? = null
    public fun setDb(db:SQLiteDatabase){
        this.db=db;
    }
    fun transform(c: Cursor): Sala {
        var item=Sala()
        item.id=c.getLong(c.getColumnIndexOrThrow("id"));
        item.estado=c.getInt(c.getColumnIndexOrThrow("estado"))>0;
        item.nombre=c.getString(c.getColumnIndexOrThrow("nombre"));
        item.descripcion=c.getString(c.getColumnIndexOrThrow("descripcion"));
        item.filas=c.getInt(c.getColumnIndexOrThrow("filas"))
        item.columnas=c.getInt(c.getColumnIndexOrThrow("columnas"))
        return item;
    }
    override suspend fun getAll(): List<Sala> {
        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nombre TEXT,"+
                "descripcion TEXT, " +
                "estado INTEGER DEFAULT 1 NOT NULL, " +
                "filas INTEGER DEFAULT 1 NOT NULL,"+
                "columnas INTEGER DEFAULT 1 NOT NULL);"
        val projection = arrayOf("id", "nombre",  "descripcion","estado","filas","columnas")
        var elements = ArrayList<Sala>();
        val cursor = db?.query(
            TABLENAME,   // The table to query
            projection,             // The array of columns to return (pass null to get all)
            null,              // The columns for the WHERE clause
            null,          // The values for the WHERE clause
            null,                   // don't group the rows
            null,                   // don't filter by row groups
            null               // The sort order
        )
        while(cursor!!.moveToNext()){
            elements.add(transform(cursor))

        }

        cursor.close()
        return elements
    }

    override suspend fun remove(item: Sala) {
        this.removeById(item.id)

    }

    override suspend fun removeById(id: Long) {
        val selection = "id = ?"
        val selectionArgs = arrayOf(id.toString())
        db?.delete(TABLENAME, selection, selectionArgs);
    }

    override suspend fun getById(id: Long): Sala? {
        TODO("Not yet implemented")
    }

    override suspend fun update(item: Sala) {
        this.updateById(item,item.id)
    }

    override suspend fun updateById(item: Sala, id: Long) {
        val values = ContentValues().apply {
            put("nombre",item.nombre)
            put("estado",item.estado)
            put("nombre",item.nombre)
            put("descripcion",item.descripcion)
            put("filas",item.filas)
            put("columnas",item.columnas)
        }


        val selection = "id = ?"
        val selectionArgs = arrayOf(item.id.toString())
        db?.update(
            TABLENAME,
            values,
            selection,
            selectionArgs)

    }

    override suspend fun add(item: Sala) {

        val values = ContentValues().apply {
            put("nombre",item.nombre)
            put("estado",item.estado)
            put("nombre",item.nombre)
            put("descripcion",item.descripcion)
            put("filas",item.filas)
            put("columnas",item.columnas)
        }
        var id=db?.insert(TABLENAME,null,values);
        item.id= id!!.toLong();

    }

}
