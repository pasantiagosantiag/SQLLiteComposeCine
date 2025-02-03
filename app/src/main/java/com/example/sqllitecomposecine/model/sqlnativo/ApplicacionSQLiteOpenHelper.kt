package com.example.sqllitecomposecine.model.sqlnativo

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

 class AplicacionSQLiteOpenHelper(context:Context): SQLiteOpenHelper(context, DATABASE_NAME,null,
     DATABASE_VERSION) {
     companion object {
         private const val DATABASE_NAME = "CineNativo"
         private const val DATABASE_VERSION = 1
         private  var conexion:AplicacionSQLiteOpenHelper? = null

         // Crear la tabla1
         private const val CREATE_PELICULA:String = "CREATE TABLE PELICULA" +
                 " (id INTEGER PRIMARY KEY AUTOINCREMENT," +
                 "nombre TEXT," +
                 "fecha DATETIME DEFAULT CURRENT_TIMESTAMP,"+
                 "valoracion INTEGER,"+
                 "duracion INTEGER,"+
                 "activo INTEGER DEFAULT 1 NOT NULL," +
                 " descripcion text);"
         private const val DROP_PELICULA="DROP TABLE IF EXISTS PELICULA"
         // Crear la tabla2
         private const val CREATE_SALA = "CREATE TABLE SALA (" +
                 "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                 "nombre TEXT,"+
                 "descripcion TEXT, " +
                 "estado INTEGER DEFAULT 1 NOT NULL, " +
                 "filas INTEGER DEFAULT 1 NOT NULL,"+
                 "columnas INTEGER DEFAULT 1 NOT NULL);"
                // "FOREIGN KEY (categoriaid) REFERENCES CATEGORIA(id));"
         private const val DROP_SALA="DROP TABLE IF EXISTS SALA"
         // Crear la tabla3
         private const val CREATE_SESION = "CREATE TABLE SESION (" +
                 "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                 "fechayhora LONG,"+
                 "pelicula INT ,"+
                 "sala INT, " +
                 "FOREIGN KEY (sala) REFERENCES SALA(id), "+
          "FOREIGN KEY (pelicula) REFERENCES PELICULA(id));"
         private const val DROP_SESION="DROP TABLE IF EXISTS SESION"

        public fun getInstance(context: Context):AplicacionSQLiteOpenHelper{
            if(conexion==null){
                conexion=AplicacionSQLiteOpenHelper(context)
            }
            return conexion!!
        }
     }
     override fun onCreate(db: SQLiteDatabase?) {
         if (db != null) {
             db.execSQL(CREATE_PELICULA)
             db.execSQL(CREATE_SALA)
             db.execSQL(CREATE_SESION)
         }

     }

     override fun onUpgrade(db: SQLiteDatabase?, oldversion: Int, newversion: Int) {
        if(db!=null){
            db.execSQL(DROP_PELICULA)
            db.execSQL(DROP_SALA)
            db.execSQL(DROP_SESION)
        }
     }
 }