package com.example.sqllitecomposecine.model.repositorios.nativo

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.example.sqllitecomposecine.model.entidades.Pelicula
import com.example.sqllitecomposecine.model.entidades.Sala
import com.example.sqllitecomposecine.model.entidades.Sesion
import com.example.sqllitecomposecine.model.repositorios.ASesionRepositorio
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZoneOffset


class SesionRepositoryNativo() : ASesionRepositorio() {

    private var TABLENAME: String = "SESION"
    private lateinit var db: SQLiteDatabase
    public fun setDb(db: SQLiteDatabase) {
        this.db = db;
    }

    fun transform(c: Cursor): Sesion {

        var item = Sesion()
        var pelicula = Pelicula()
        pelicula.id = c.getLong(c.getColumnIndexOrThrow("p.id"))
        pelicula.nombre = c.getString(c.getColumnIndexOrThrow("p.nombre"))
        pelicula.descripcion = c.getString(c.getColumnIndexOrThrow("p.descripcion"))
        pelicula.activo = c.getInt(c.getColumnIndexOrThrow("activo")) > 0;
        pelicula.duracion = c.getInt(c.getColumnIndexOrThrow("duracion"))
        pelicula.valoracion = c.getInt(c.getColumnIndexOrThrow("valoracion"))
        var sala = Sala();
        sala.id = c.getLong(c.getColumnIndexOrThrow("sa.id"))
        sala.nombre = c.getString(c.getColumnIndexOrThrow("sa.nombre"))
        sala.descripcion = c.getString(c.getColumnIndexOrThrow("sa.descripcion"))
        sala.filas = c.getInt(c.getColumnIndexOrThrow("sa.filas"))
        sala.columnas = c.getInt(c.getColumnIndexOrThrow("sa.columnas"))
        sala.estado = c.getInt(c.getColumnIndexOrThrow("sa.estado")) > 0

        item.id = c.getLong(c.getColumnIndexOrThrow("s.id"));
        item.pelicula = pelicula
        item.sala = sala
        item.fecha_y_hora = Instant.ofEpochSecond(c.getLong(c.getColumnIndexOrThrow("fechayhora")))
            .atZone(ZoneId.systemDefault()) // Usa la zona horaria local
            .toLocalDateTime();
        return item;
    }

    override suspend fun getAll(): List<Sesion> {
        val sql = "select s.id, s.fechayhora, s.pelicula, s.sala, " +
                "sa.id, sa.nombre, sa.descripcion, sa.estado, sa.filas, sa.columnas, " +
                "p.id, p.nombre, p.fecha, p.valoracion, p.duracion, p.activo,p.descripcion" +
                " from SESION as s LEFT JOIN PELICULA as p ON s.pelicula=p.id LEFT JOIN SALA AS sa ON s.sala=sa.id";

        var elements = ArrayList<Sesion>();
        val cursor = db.rawQuery(sql, null)

        while (cursor.moveToNext()) {
            elements.add(transform(cursor))

        }

        cursor.close()
        return elements
    }

    override suspend fun remove(item: Sesion) {
        this.removeById(item.id)
    }

    override suspend fun removeById(id: Long) {
        val selection = "id = ?"
        val selectionArgs = arrayOf(id.toString())
        db.delete(TABLENAME, selection, selectionArgs);
    }

    override suspend fun getById(id: Long): Sesion? {
        TODO("Not yet implemented")
    }

    override suspend fun update(item: Sesion) {
        this.updateById(item, item.id)
    }

    override suspend fun updateById(item: Sesion, id: Long) {

        val values = ContentValues().apply {
            put("pelicula", item.pelicula.id)
            put("sala", item.sala.id)
            put("fechayhora", item.fecha_y_hora.toEpochSecond(ZoneOffset.UTC))


        }


        val selection = "id = ?"
        val selectionArgs = arrayOf(item.id.toString())
        db.update(
            TABLENAME,
            values,
            selection,
            selectionArgs
        )

    }

    override suspend fun add(item: Sesion) {

        val values = ContentValues().apply {
            put("pelicula", item.pelicula.id)
            put("sala", item.sala.id)
            put("fechayhora", item.fecha_y_hora.toEpochSecond(ZoneOffset.UTC))


        }
        var id = db.insert(TABLENAME, null, values);
        item.id = id.toLong();

    }

}
