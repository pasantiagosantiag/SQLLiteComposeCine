package com.example.sqllitecomposecine.ui.componentes.commons

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import java.io.File

@Composable
fun CameraPhoto(contexto: Context, modifier: Modifier, onImageSelected:(Uri)->Unit) {
    var photoUri= remember { mutableStateOf<Uri>(Uri.parse("")) }
    Column(modifier = modifier) {
        val cameraLauncher =
            rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { isImageSaved ->
                //se ha almacenado la imagen en
                if (isImageSaved) {
                    //se ejecuta la lambda, pasando como parámetro la uri en la que se ha almacenado
                    //la imagen
                    onImageSelected(photoUri.value)
                    //se resetea para la próxima vez
                    val dir = File(contexto.cacheDir, "images").apply { if (!exists()) mkdirs() }

                    val f = File(dir, "photo_${System.currentTimeMillis()}.jpg")
                    photoUri.value = FileProvider.getUriForFile(contexto, "com.example.sqllitecomposecine.fileprovider", f)

                } else {
                    Toast.makeText(contexto, "Ha ocurrido un fallo, puede ser por el proveedor de ficheros", Toast.LENGTH_LONG)
                }
            }
        val permissionLauncher =
            rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { permissionGranted ->
                if (permissionGranted) {
                    //val dir = File(contexto.filesDir, "images").apply { if (!exists()) mkdirs() }
                    val dir = File(contexto.cacheDir, "images").apply { if (!exists()) mkdirs() }

                    val f = File(dir, "photo_${System.currentTimeMillis()}.jpg")
                     photoUri.value = FileProvider.getUriForFile(contexto, "com.example.sqllitecomposecine.fileprovider", f)

                    cameraLauncher.launch(photoUri.value)

                } else {

                    Toast.makeText(contexto, "Permisos no concedidos", Toast.LENGTH_LONG)

                }
            }
        Button(onClick = {
            permissionLauncher.launch(android.Manifest.permission.CAMERA)
            //cameraLauncher.launch(photoUri)
        }, modifier = Modifier) {
            androidx.compose.material3.Icon(
                imageVector = Icons.Filled.PhotoCamera,
                contentDescription = "Hacer fotografía",
            )
        }
    }
}