package com.example.sqllitecomposecine.ui.componentes.sesiones

import androidx.compose.foundation.layout.fillMaxWidth

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowUp

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.sqllitecomposecine.model.entidades.Pelicula

import org.koin.androidx.compose.koinViewModel

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun PeliculaComboBox(items: State<List<Pelicula>?>, item: State<Pelicula>, alcambiar:   (Pelicula) -> Unit) {


    // State to manage the selected item and menu visibility
    var selectedPelicula by remember { mutableStateOf<Pelicula?>(item.value) }

    var expanded by remember { mutableStateOf(false) }
    LaunchedEffect(item) {
       item.let {
            selectedPelicula = it.value


        }

    }


    //selected.categoria?.let { Text( it.nombre) }
    ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = !expanded }) {
        // Textfield that shows the selected item or a hint
        TextField(
            value = selectedPelicula?.nombre ?: "Selecciona una localidad",
            onValueChange = {

            },
            readOnly = true,
            label = { Text("Pelicula") },
            trailingIcon = {
                Icon(
                    imageVector = if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.ArrowDropDown,
                    contentDescription = null
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor()
        )

        // Dropdown menu with the list of localidades
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            items.value?.forEach { cat ->
                DropdownMenuItem(
                    onClick = {
                        run {
                            selectedPelicula = cat
                             alcambiar(cat)
                            expanded = false
                        }
                    },
                    text = { Text(text = cat.nombre) },

                )
               /* DropdownMenuItem(onClick = {
                   /* selectedPelicula = item
                    expanded = false

                    alcambiar(localidad.pueblo)*/
                }) {
                    Text(text = localidad.pueblo)
                }*/
            }
        }
    }
}