package com.example.sqllitecomposecine.servicios

import android.content.ContentResolver
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.OpenableColumns
import com.example.sqllitecomposecine.model.entidades.Pelicula
import com.example.sqllitecomposecine.model.repositorios.APeliculaRepositorio
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream

class PeliculaServicio (var repositorio:APeliculaRepositorio, var contexto:Context) {
    suspend fun  insert(pelicula: Pelicula){
        repositorio.add(pelicula)
        var uri=Uri.parse(pelicula.uri)
        var filename=getFileName(contexto,uri)
        var extension= filename?.substringAfterLast('.', "")
        var nuevoNombre="pelicula${pelicula.id}.${extension}"
        pelicula.uri=nuevoNombre
        repositorio.update(pelicula)
        copyFileFromUri(uri,nuevoNombre)
    }
    suspend fun update(pelicula:Pelicula){
        repositorio.update(pelicula)
    }
    suspend fun getAll():List<Pelicula>{
        return repositorio.getAll()
    }
    suspend fun delete(pelicula:Pelicula){
        val file = File(contexto.filesDir, pelicula.uri)
        if( file.exists())
            file.delete()
        repositorio.removeById(pelicula.id)
    }
    fun copyFileFromUri( uri: Uri, destinationFileName: String): File? {
        val contentResolver: ContentResolver = contexto.contentResolver
        contexto.contentResolver.getType(uri)
        // Crear el archivo destino en el directorio de datos de la app
        val destinationFile = File(contexto.filesDir, destinationFileName)

        contentResolver.openInputStream(uri)?.use { inputStream ->
            FileOutputStream(destinationFile).use { outputStream ->
                copyStream(inputStream, outputStream)
            }
        } ?: return null

        return destinationFile
    }
    fun copyStream(input: InputStream, output: OutputStream) {
        val buffer = ByteArray(1024)
        var length: Int
        while (input.read(buffer).also { length = it } > 0) {
            output.write(buffer, 0, length)
        }
    }
    fun getFileName(context: Context, uri: Uri): String? {
        var name: String? = null
        val cursor: Cursor? = context.contentResolver.query(uri, null, null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                name = it.getString(it.getColumnIndexOrThrow(OpenableColumns.DISPLAY_NAME))
            }
        }
        return name
    }
}