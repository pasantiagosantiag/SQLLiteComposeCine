package com.example.sqllitecomposecine.ui.componentes.sesiones

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.sqllitecomposecine.model.entidades.Sesion

@Composable
fun SesionItem(
    item:Sesion,
    ver: () -> Unit,
    editar: () -> Unit,
    borrar: () -> Unit

) {
    Row(modifier = Modifier) {
        Text(
            item.pelicula.nombre,//.substring(kotlin.math.min(item.pelicula.nombre.length,15)), //+ item.fecha_y_hora.format("dd/mm/YYYU"),
            modifier = Modifier
                .clickable {
                    ver()


                }
                //.weight(0.25f)
                     )
        Button(onClick = {

            editar()

        }, modifier = Modifier //.weight(0.25f)
        ) {
            androidx.compose.material3.Icon(
                imageVector = Icons.Filled.Edit,
                contentDescription = "Editar",
            )
        }
        Button(onClick = {
            borrar()

        }, modifier = Modifier//.weight(0.25f)
        ) {
            androidx.compose.material3.Icon(
                imageVector = Icons.Filled.Delete,
                contentDescription = "Borrar",
            )
        }
    }
}