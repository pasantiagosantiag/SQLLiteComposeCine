package com.example.sqllitecomposecine.ui.componentes.commons

import android.content.ContentResolver
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.graphics.TypefaceCompatUtil
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream


@Composable
fun ImagePickerWithPermission() {
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var hasPermission by remember { mutableStateOf(false) }
    val context = LocalContext.current

    // Launcher para solicitar permisos
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            hasPermission = isGranted
        }
    )



    // Verifica y solicita permisos al iniciar
   /* LaunchedEffect(Unit) {
        permissionLauncher.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)
    }*/
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
    fun copyFileFromUri( uri: Uri, destinationFileName: String): File? {
        val contentResolver: ContentResolver = context.contentResolver
        context.contentResolver.getType(uri)
        // Crear el archivo destino en el directorio de datos de la app
        val destinationFile = File(context.filesDir, destinationFileName)

        contentResolver.openInputStream(uri)?.use { inputStream ->
            FileOutputStream(destinationFile).use { outputStream ->
                copyStream(inputStream, outputStream)
            }
        } ?: return null

        return destinationFile
    }
    // Launcher para seleccionar una imagen de la galería
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            imageUri = uri // Guarda la URI de la imagen seleccionada

            copyFileFromUri(uri!!,getFileName(context,uri)!!)
            Log.i("log",uri.toString())
        }
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (hasPermission) {
            // Botón para abrir la galería
            Button(onClick = { imagePickerLauncher.launch("image/*") }) {
                Text("Seleccionar imagen")
            }

            Spacer(modifier = Modifier.height(16.dp))


        } else {
            Text("Permiso denegado. No se puede acceder a la galería.",
                modifier=Modifier.clickable {

                })
            Button(onClick = {
               // permissionLauncher.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                imagePickerLauncher.launch("image/*")
            }) {
                Text("permiso")
            }
        }
    }



}