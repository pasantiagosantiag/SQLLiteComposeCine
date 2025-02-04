package com.example.sqllitecomposecine.ui.componentes.sesiones

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun SesionBuscador(
    modifier: Modifier=Modifier,
    buscadorpelicula: MutableStateFlow<String>,
    buscadorsala:MutableStateFlow<String>
   // buscadordescripcion: MutableStateFlow<String>,

) {
    val pelicula = buscadorpelicula.collectAsState()
    val sala = buscadorsala.collectAsState()

    Row(
        modifier = modifier
            .fillMaxWidth(),

        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier.weight(0.5f).padding(horizontal = 2.dp)
        ) {
            OutlinedTextField(
                value = pelicula.value,
                //se elimina el tabulador
                onValueChange = { buscadorpelicula.value = it.replace("\t", "").replace("\n","") },
                label = { Text("Nombre") },
                modifier = Modifier.fillMaxWidth()
            )
        }
        Column(
            modifier = Modifier.weight(0.5f).padding(horizontal = 2.dp)
        ) {
            OutlinedTextField(
                value = sala.value,
                onValueChange = { buscadorsala.value = it.replace("\t", "").replace("\n","") },
                label = { Text("Descripcion") },
                modifier = Modifier.fillMaxWidth()
            )
        }


    }
}
