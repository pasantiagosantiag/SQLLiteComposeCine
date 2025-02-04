package com.example.sqllitecomposecine.ui.componentes.peliculas

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.sqllitecomposecine.model.entidades.Pelicula
import com.example.sqllitecomposecine.ui.componentes.commons.ImageDeDirectorioLocal

@Composable
fun PeliculaItem(
    item: Pelicula,
    context: Context,
    ver: () -> Unit,
    editar: () -> Unit,
    borrar: () -> Unit
) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Text("${item.nombre} ${item.id}",
            modifier = Modifier
                .clickable {
                    ver()


                }
                .weight(0.50f))
        ImageDeDirectorioLocal(modifier=Modifier.weight(0.25f),fileName = item.uri, context = context )

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