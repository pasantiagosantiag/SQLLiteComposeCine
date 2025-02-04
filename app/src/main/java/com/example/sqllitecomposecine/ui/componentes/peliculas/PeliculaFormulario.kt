package com.example.sqllitecomposecine.ui.componentes.peliculas

import android.annotation.SuppressLint
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.example.sqllitecomposecine.model.entidades.Pelicula
import com.example.sqllitecomposecine.ui.componentes.commons.ImageDeDirectorioLocal
import com.example.sqllitecomposecine.ui.componentes.commons.ImagePickerWithPermission
import com.example.sqllitecomposecine.ui.viewmodels.PeliculaViewModel
import org.koin.androidx.compose.koinViewModel

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun PeliculaFormulario(
    expandido: Boolean, editable: State<Boolean>, save: (item: Pelicula) -> Unit, atras: () -> Unit
) {
    // Estados para los campos
    val vm: PeliculaViewModel = koinViewModel()
    val selected by vm.selected.collectAsState()


    var nombre by remember { mutableStateOf("") }//vm.selected.value.nombre) }
    var activo by remember { mutableStateOf(false) }
    var descripcion by remember { mutableStateOf("") }
    var valoracion by remember { mutableStateOf<Int>(0) }
    var duracion by remember { mutableStateOf<Int>(0) }
    var url by remember { mutableStateOf<Uri?>(null) }

    LaunchedEffect(selected) {
        nombre = selected.nombre
        activo = selected.activo
        descripcion = selected.descripcion
        valoracion = selected.valoracion
        duracion = selected.duracion
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
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                "Activo"
            )
            Checkbox(checked = activo, enabled = editable.value, onCheckedChange = { activo = it })
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            /*Text(
                "Filas",
                modifier = Modifier.weight(0.2f)

            )*/
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
        if(vm.selected.value.id<1)
            ImagePickerWithPermission(onselect = { url = it })
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
                    if (selected == null || selected.id < 1) {
                        u = Pelicula()

                    } else {
                        u = selected
                    }
                    u?.nombre = nombre.toString()
                    u?.activo = activo
                    u?.descripcion = descripcion.toString()
                    u?.valoracion = valoracion
                    u?.duracion = duracion
                    u?.uri = url.toString()
                    u?.let { save(it) }

                },


                ) {
                Text("Guardar")
            }
            if (!expandido) {
                Button(
                    onClick = {
                        atras()
                    }, modifier = Modifier.padding(start = 10.dp)

                ) {
                    Text("Volver")
                }
            }
        }
    }
}