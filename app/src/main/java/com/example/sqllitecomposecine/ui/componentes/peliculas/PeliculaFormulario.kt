package com.example.sqllitecomposecine.ui.componentes.peliculas

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import coil3.compose.rememberAsyncImagePainter
import com.example.sqllitecomposecine.model.entidades.Pelicula
import com.example.sqllitecomposecine.ui.componentes.commons.CameraPhoto

import com.example.sqllitecomposecine.ui.viewmodels.PeliculaViewModel
import org.koin.androidx.compose.koinViewModel

import java.io.File


@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun PeliculaFormulario(
    expandido: Boolean, editable: State<Boolean>, save: (item: Pelicula) -> Unit, atras: () -> Unit,
    context: Context,
) {
    // Estados para los campos
    val vm: PeliculaViewModel = koinViewModel()
    val selected by vm.selected.collectAsState()
    var nombre by remember { mutableStateOf("") }//vm.selected.value.nombre) }
    var activo by remember { mutableStateOf(false) }
    var descripcion by remember { mutableStateOf("") }
    var valoracion by remember { mutableStateOf<Int>(0) }
    var duracion by remember { mutableStateOf<Int>(0) }
    var uri by remember { mutableStateOf<Uri>(Uri.parse("")) }
    var id by remember { mutableStateOf(0L) }
    //cambio del viewmodel
    LaunchedEffect(selected) {
        nombre = selected.nombre
        activo = selected.activo
        descripcion = selected.descripcion
        valoracion = selected.valoracion
        duracion = selected.duracion
       // fichero = selected.uri
        id = selected.id
        val file = File(context.filesDir, selected.uri)
        if(file.exists() && !file.isDirectory)
            uri = file.toUri()
        else
            uri=Uri.parse("")

    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {


        // Campo de nombre
        OutlinedTextField(
            value = nombre,
            onValueChange = {
                nombre = it.replace("\t", "").replace("\n", "");
            },
            enabled = editable.value,
            label = { Text("Nombre") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = descripcion, onValueChange = {
                descripcion = it.replace("\t", "").replace("\n", "");
            }, enabled = editable.value,

            label = { Text("Descripcion") }, modifier = Modifier.fillMaxWidth()
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Activo")
            Checkbox(checked = activo, enabled = editable.value, onCheckedChange = { activo = it })
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(
                value = if (valoracion > 0) valoracion.toString() else "", onValueChange = { it ->
                    if (it.length <= 3) {
                        var tempo = it.replace("\t", "").replace("\n", "").filter { it.isDigit() }
                        if (!tempo.equals("")) valoracion = tempo.toInt()
                    }

                }, enabled = editable.value,
                label = { Text("Valoración") }, modifier = Modifier.fillMaxWidth()//.weight(0.8f)
            )
        }
       
        if (!uri.toString().equals("")) {
            Image(
                painter = rememberAsyncImagePainter(uri),
                contentDescription = "imagen",
                modifier = Modifier.weight(1f)
            )
        }
        else {
            Text(text = selected.nombre+ " "+selected.uri)
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {

            OutlinedTextField(
                value = if (duracion > 0) duracion.toString() else "", onValueChange = { it ->
                    if (it.length <= 3) {
                        var tempo = it.replace("\t", "").replace("\n", "").filter { it.isDigit() }
                        if (!tempo.equals("")) duracion = it.filter { it.isDigit() }.toInt()
                    }

                }, enabled = editable.value,

                label = { Text("Duración") }, modifier = Modifier.fillMaxWidth() //.weight(0.8f)
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)

                .alpha(if (editable.value == true) 1.0f else 0.0f),
            horizontalArrangement = Arrangement.Center, // Centers content horizontally
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Botón de envío
            Button(
                onClick = {
                    var u: Pelicula?
                    //si es nuevo
                    if (selected == null || selected.id < 1) {
                        u = Pelicula()
                        //solo si es la primera fotografia
                        u?.uri = uri.toString()
                    } else {
                        u = selected
                    }
                    u?.nombre = nombre.toString()
                    u?.activo = activo
                    u?.descripcion = descripcion.toString()
                    u?.valoracion = valoracion
                    u?.duracion = duracion
                    u?.let { save(it)
                    vm.unSelect()
                   // selected=vm.selected.collectAsState()
                    }
                    Toast.makeText(context,"Acción realizada con  éxito", Toast.LENGTH_LONG)


                }
            ) {
                androidx.compose.material3.Icon(
                    imageVector = Icons.Filled.Save,
                    contentDescription = "Guardar",
                )
            }
             if (id <= 0){
                 CameraPhoto(context, modifier = Modifier

                     .weight(0.2f),
                     onImageSelected = {
                         uri = it
                     }

                 )
             }


            if (!expandido) {
                Button(
                    onClick = {
                        atras()
                    }, modifier = Modifier.padding(start = 10.dp)

                ) {
                    androidx.compose.material3.Icon(
                        imageVector = Icons.Filled.ArrowBackIosNew,
                        contentDescription = "Volver",
                    )
                }
            }
        }
    }
}