package com.example.sqllitecomposecine.ui.componentes.salas

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.sqllitecomposecine.model.entidades.Sala

@Composable
fun SalaItem(
    item: Sala,

    ver: () -> Unit,
    editar: () -> Unit,
    borrar: () -> Unit
) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Text("${item.nombre}",
            modifier = Modifier
                .clickable {
                    ver()


                }
                .weight(0.50f)
        )
        Button(onClick = {

            editar()

        }, modifier = Modifier.weight(0.25f)) {
            Text(text = "Editar")
        }
        Button(onClick = {
            borrar()

        }, modifier = Modifier.weight(0.25f)) {
            Text(text = "Borrar")
        }
    }
}