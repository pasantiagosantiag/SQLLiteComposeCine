package com.example.sqllitecomposecine.ui.componentes.sesiones

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
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
import com.example.sqllitecomposecine.model.entidades.Sala
import com.example.sqllitecomposecine.model.entidades.Sesion
import com.example.sqllitecomposecine.ui.componentes.commons.DatePickerFieldToModal
import com.example.sqllitecomposecine.ui.viewmodels.SesionViewModel
import org.koin.androidx.compose.koinViewModel
import java.time.LocalDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SesionFormulario(
    expandido: Boolean, editable: State<Boolean>,
    peliculas: State<List<Pelicula>>,
    salas: State<List<Sala>>,
    item: Sesion,
    save: (item: Sesion) -> Unit, atras: () -> Unit
) {
    // Estados para los campos
    var fecha = remember { mutableStateOf(LocalDateTime.now()) }//vm.selected.value.nombre) }
    var pelicula = remember { mutableStateOf(Pelicula()) }
    var sala = remember { mutableStateOf(Sala()) }




    LaunchedEffect(item) {
        item?.let {
            fecha.value = it.fecha_y_hora
            pelicula.value = it.pelicula;
            sala.value = it.sala
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        DatePickerFieldToModal(
            modifier = Modifier,
            editable = editable,
            selectedDate = fecha,
            onDateSelected = {}
        )
        // Campo de nombre
       /* OutlinedTextField(
            value = pelicula.value.toString(),
            onValueChange = {
            },
            enabled = editable.value,
            label = { Text("Nombre") },
            modifier = Modifier.fillMaxWidth()
        )*/
        PeliculaComboBox(items = peliculas, item = pelicula) {
            pelicula.value = it
        }
        SalaComboBox(items = salas, item = sala) {
            sala.value = it
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
                    var u: Sesion?
                    if (item == null || item.id < 1) {
                        u = Sesion()

                    } else {
                        u = item
                    }
                    u?.fecha_y_hora = fecha.value
                    u?.sala = sala.value
                    u?.pelicula = pelicula.value

                    u?.let { save(it) }

                },


                ) {
                androidx.compose.material3.Icon(
                    imageVector = Icons.Filled.Save,
                    contentDescription = "Guardar",
                )
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