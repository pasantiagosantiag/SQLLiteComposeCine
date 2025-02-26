package com.example.sqllitecomposecine.ui.componentes.peliculas

import android.content.Context
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
import com.example.sqllitecomposecine.model.entidades.Pelicula


@Composable
fun PeliculaItem(
    item: Pelicula,
    context: Context,
    ver: () -> Unit,
    editar: () -> Unit,
    borrar: () -> Unit
) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Text("${item.nombre} ${item.id}", modifier = Modifier.clickable { ver() }.weight(0.40f))
        //ImageDeDirectorioLocal(modifier=Modifier.weight(0.20f),fileName = item.uri, context = context )

        Button(onClick = { editar() }, modifier = Modifier.weight(0.20f)) {
            androidx.compose.material3.Icon(
                imageVector = Icons.Filled.Edit,
                contentDescription = "Editar",
              )
        }
        Button(onClick = { borrar() }, modifier = Modifier.weight(0.20f)) {
            androidx.compose.material3.Icon(
                imageVector = Icons.Filled.Delete,
                contentDescription = "Borrar",
            )
        }
    }
}