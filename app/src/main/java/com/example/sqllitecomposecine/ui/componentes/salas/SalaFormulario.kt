package com.example.sqllitecomposecine.ui.componentes.salas

import android.util.Log
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
import com.example.sqllitecomposecine.model.entidades.Sala
import com.example.sqllitecomposecine.ui.viewmodels.SalaViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun SalaFormulario(
    expandido: Boolean, editable: State<Boolean>, save: (item: Sala) -> Unit, atras: () -> Unit
) {
    // Estados para los campos
    val vm: SalaViewModel = koinViewModel()
    val selected by vm.selected.collectAsState()


    var nombre by remember { mutableStateOf("") }//vm.selected.value.nombre) }
    var estado by remember { mutableStateOf(false) }
    var descripcion by remember { mutableStateOf("") }
    var filas by remember { mutableStateOf<Int>(0) }
    var columnas by remember { mutableStateOf<Int>(0) }


    LaunchedEffect(selected) {
        nombre = selected.nombre
        estado = selected.estado
        descripcion = selected.descripcion
        filas = selected.filas
        columnas = selected.columnas
    }



    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {


        // Campo de nombre
        OutlinedTextField(value = nombre,
            onValueChange = {
                nombre = it.replace("\t", "").replace("\n","");
            },
            enabled = editable.value,
            label = { Text("Nombre") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = descripcion, onValueChange = {
                descripcion =  it.replace("\t", "").replace("\n","");
            }, enabled = editable.value,

            label = { Text("Descripcion") }, modifier = Modifier.fillMaxWidth()
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                "Activo"
            )
            Checkbox(checked = estado, enabled = editable.value, onCheckedChange = { estado = it })
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            /*Text(
                "Filas",
                modifier = Modifier.weight(0.2f)

            )*/
            OutlinedTextField(
                value = if (filas > 0) filas.toString() else "", onValueChange = { it ->
                    if (it.length <= 3) {
                        var tempo =  it.replace("\t", "").replace("\n","").filter { it.isDigit() }
                        if (!tempo.equals("")) filas = tempo.toInt()
                    }

                }, enabled = editable.value,

                label = { Text("Filas") }, modifier = Modifier.fillMaxWidth()//.weight(0.8f)
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            /*Text(
                "Columnas",
                modifier = Modifier.weight(0.2f)
            )*/
            OutlinedTextField(
                value = if (columnas > 0) columnas.toString() else "", onValueChange = { it ->
                    if (it.length <= 3) {
                        var tempo =  it.replace("\t", "").replace("\n","").filter { it.isDigit() }
                        if (!tempo.equals("")) columnas = it.filter { it.isDigit() }.toInt()
                    }

                }, enabled = editable.value,

                label = { Text("Columnas") }, modifier = Modifier.fillMaxWidth() //.weight(0.8f)
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
                    var u: Sala?
                    if (selected == null || selected.id < 1) {
                        u = Sala()

                    } else {
                        u = selected
                    }
                    u?.nombre = nombre.toString()
                    u?.estado = estado
                    u?.descripcion = descripcion.toString()
                    u?.filas = filas
                    u?.columnas = columnas
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